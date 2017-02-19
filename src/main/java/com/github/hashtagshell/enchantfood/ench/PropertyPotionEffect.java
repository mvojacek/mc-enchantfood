package com.github.hashtagshell.enchantfood.ench;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

import com.github.hashtagshell.enchantfood.potion.PotionCategory;
import com.github.hashtagshell.enchantfood.reference.Ref;
import com.github.hashtagshell.enchantfood.utility.*;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;

import static com.github.hashtagshell.enchantfood.reference.Ref.Nbt.TagType.*;

public class PropertyPotionEffect implements INBTSerializer<PropertyPotionEffect, NBTTagList>
{
    private Map<Potion, PotionEffect> effects = new HashMap<>();

    //TODO Add the transformer or event handler (props handler) that actually gives the effects when eaten

    public PropertyPotionEffect(PotionEffect... effects)
    {
        addEffects(effects);
    }

    public Map<Potion, PotionEffect> getEffectMap()
    {
        return effects;
    }

    public Set<PotionEffect> getEffects()
    {
        return new HashSet<>(effects.values());
    }

    public boolean isEmpty()
    {
        return effects.size() == 0;
    }

    public PropertyPotionEffect clearEffects()
    {
        effects.clear();
        return this;
    }

    public boolean hasEffect(Potion potion)
    {
        return effects.containsKey(potion);
    }

    public PotionEffect getEffect(Potion potion)
    {
        return effects.get(potion);
    }

    public PropertyPotionEffect addEffect(PotionEffect effect)
    {
        effects.put(effect.getPotion(), effect);
        return this;
    }

    public PropertyPotionEffect addEffects(PotionEffect... effects)
    {
        for (PotionEffect effect : effects)
            addEffect(effect);
        return this;
    }

    public PropertyPotionEffect removeEffect(Potion potion)
    {
        effects.remove(potion);
        return this;
    }

    public PropertyPotionEffect replaceEffect(Potion potion, PotionEffect with)
    {
        if (hasEffect(potion))
        {
            effects.put(with.getPotion(), with);
            effects.remove(potion);
        }
        return this;
    }

    public PropertyPotionEffect forEach(Consumer<PotionEffect> consumer)
    {
        effects.values().forEach(consumer);
        return this;
    }

    public PropertyPotionEffect processEffect(Potion potion, Function<PotionEffect, PotionEffect> function)
    {
        if (hasEffect(potion))
        {
            PotionEffect out = function.apply(effects.remove(potion));
            if (out != null) effects.put(out.getPotion(), out);
        }
        return this;
    }

    public PropertyPotionEffect apply(PropertyPotionEffect prop)
    {
        prop.effects.forEach((k, v) -> effects.put(k, v));
        return this;
    }

    public PropertyPotionEffect applyToEntity(EntityLivingBase entity)
    {
        effects.values().forEach(entity::addPotionEffect);
        return this;
    }

    public List<String> getToolTip()
    {
        class LineMetadata implements Comparable<LineMetadata>
        {
            public String         particles;
            public PotionCategory category;
            public String         locName;
            public int            amplifier;
            public int            mins;
            public int            secs;

            public String get()
            {
                return particles + category.getFormattingCode() + locName + " x" + amplifier + " for " + mins + ":" + secs;
            }

            @Override
            public int compareTo(LineMetadata o)
            {
                int i = category.compareTo(o.category);
                return i != 0 ? i : locName.compareTo(o.locName);
            }
        }

        SortedSet<LineMetadata> set = new TreeSet<>();
        effects.values().forEach(effect ->
                                 {
                                     LineMetadata meta = new LineMetadata();
                                     //FIXME this does not seem to have an effect in tooltips, investigate
                                     meta.particles = effect.showParticles ? ChatColor.ITALIC.toString() : "";
                                     meta.category = PotionCategory.ofPotion(effect.getPotion());
                                     meta.locName = Log.translate(effect.getPotion().getName());
                                     meta.amplifier = effect.getAmplifier();
                                     meta.mins = effect.getDuration() / 60;
                                     meta.secs = effect.getDuration() % 60;
                                     set.add(meta);
                                 });
        return Array.processToList(set, LineMetadata::get);
    }

    @Override
    public NBTTagList serialize()
    {
        NBTTagList list = new NBTTagList();
        effects.values().forEach(effect -> list.appendTag(effect.writeCustomPotionEffectToNBT(new NBTTagCompound())));
        return list;
    }

    @Override
    public PropertyPotionEffect deserialize(NBTTagList list)
    {
        clearEffects();
        for (int i = 0; i < list.tagCount(); i++)
        {
            NBTTagCompound tag = list.getCompoundTagAt(i);
            if (tag.hasKey("Id", BYTE.id()))
            {
                PotionEffect effect = PotionEffect.readCustomPotionEffectFromNBT(tag);
                effects.put(effect.getPotion(), effect);
            }
        }
        return this;
    }

    public static PropertyPotionEffect fromStack(ItemStack stack)
    {
        NBTTagList nbt = NBT.getModTag(stack).getTagList(Ref.Nbt.LIST_COMP_FOOD_PROPERTY_POTION_EFFECT, COMPOUND.id());
        return new PropertyPotionEffect().deserialize(nbt);
    }

    public static boolean tagPresent(ItemStack stack)
    {
        return NBT.getModTag(stack).hasKey(Ref.Nbt.LIST_COMP_FOOD_PROPERTY_POTION_EFFECT, LIST.id());
    }

    public ItemStack writeToStack(ItemStack stack)
    {
        if (isEmpty() && tagPresent(stack))
        {
            NBT.getModTag(stack).removeTag(Ref.Nbt.LIST_COMP_FOOD_PROPERTY_POTION_EFFECT);
            NBT.removeModTagIfEmpty(stack);
        }
        NBT.getModTag(stack).setTag(Ref.Nbt.LIST_COMP_FOOD_PROPERTY_POTION_EFFECT, serialize());
        return stack;
    }

    public ItemStack writeAddToStack(ItemStack stack)
    {
        return fromStack(stack).apply(this).writeToStack(stack);
    }
}
