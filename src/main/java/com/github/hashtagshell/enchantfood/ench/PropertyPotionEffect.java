package com.github.hashtagshell.enchantfood.ench;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

import com.github.hashtagshell.enchantfood.reference.Ref;
import com.github.hashtagshell.enchantfood.utility.Array;
import com.github.hashtagshell.enchantfood.utility.INBTSerializer;
import com.github.hashtagshell.enchantfood.utility.NBT;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;

import static com.github.hashtagshell.enchantfood.reference.Ref.Nbt.TagType.BYTE;
import static com.github.hashtagshell.enchantfood.reference.Ref.Nbt.TagType.COMPOUND;

public class PropertyPotionEffect implements INBTSerializer<PropertyPotionEffect, NBTTagList>
{
    private Map<Potion, PotionEffect> effects = new HashMap<>();

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
        if (effects.containsKey(potion))
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
        if (effects.containsKey(potion))
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
        return Array.processToList(effects.values(), effect ->
        {
            //TODO Return tooltip line for each effect
            // {bold if onHUD}{italic if particles}{colorCode}{potion} x{modifier} for {mins}:{seconds}
            //noinspection Convert2MethodRef
            return effect.toString();
        });
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
        effects.clear();
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

    public ItemStack toStack(ItemStack stack)
    {
        NBT.getModTag(stack).setTag(Ref.Nbt.LIST_COMP_FOOD_PROPERTY_POTION_EFFECT, serialize());
        return stack;
    }

    public ItemStack addToStack(ItemStack stack)
    {
        return fromStack(stack).apply(this).toStack(stack);
    }
}
