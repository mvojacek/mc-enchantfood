package com.github.hashtagshell.enchantfood.ench;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

import com.github.hashtagshell.enchantfood.config.Conf;
import com.github.hashtagshell.enchantfood.potion.food.PotionCategory;
import com.github.hashtagshell.enchantfood.reference.Ref;
import com.github.hashtagshell.enchantfood.utility.INBTSerializer;
import com.github.hashtagshell.enchantfood.utility.Log;
import com.github.hashtagshell.enchantfood.utility.NBT;
import com.github.hashtagshell.enchantfood.utility.RomanNumerals;
import com.github.hashtagshell.enchantfood.utility.tuple.Pair;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;

import static com.github.hashtagshell.enchantfood.config.Conf.Enums.EnforceRestrictions.PREVENT_APPLY;
import static com.github.hashtagshell.enchantfood.config.Conf.Enums.EnforceRestrictions.WRITE_STACK;
import static com.github.hashtagshell.enchantfood.potion.food.PotionCategory.*;
import static com.github.hashtagshell.enchantfood.reference.Ref.Nbt.TagType.*;
import static com.github.hashtagshell.enchantfood.utility.ChatColor.*;

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

    public PropertyPotionEffect applyToEntity(EntityLivingBase entity, ItemStack stack)
    {
        List<PotionEffect> toRemove = new ArrayList<>();
        for (PotionEffect effect : effects.values())
            if (Conf.FoodPotions.enforceRestrictions.includes(PREVENT_APPLY) && clampArgs(effect))
                toRemove.add(effect);
            else
                entity.addPotionEffect(effect);
        toRemove.forEach(effects::remove);
        if (stack != null && Conf.FoodPotions.enforceRestrictions.includes(WRITE_STACK))
            writeToStack(stack);
        return this;
    }

    public PropertyPotionEffect applyToEntityUnrestricted(EntityLivingBase entity)
    {
        effects.values().forEach(entity::addPotionEffect);
        return this;
    }

    private static final Comparator<Pair<String, String>> compareKeys = (p1, p2) -> p1.getKey().compareTo(p2.getKey());

    public List<String> getToolTip(boolean advanced)
    {
        int linesTotal = Conf.Visual.foodPotionPreviewLines;
        boolean wrap = !(Conf.Visual.foodPotionPreviewFull || advanced) && effects.values().size() > linesTotal;
        SortedMap<PotionCategory, SortedSet<Pair<String, String>>> map = new TreeMap<>();
        for (PotionCategory cat : PotionCategory.values())
            map.put(cat, new TreeSet<>(compareKeys));
        effects.values().forEach(effect ->
                                 {
                                     PotionCategory category = PotionCategory.ofPotion(effect.getPotion());
                                     String name = Log.translate(effect.getPotion().getName());
                                     StringBuilder line = new StringBuilder(category.getChatFormatting());
                                     if (effect.doesShowParticles()) line.append(BOLD);
                                     line.append(name)
                                         .append(" ")
                                         .append(RomanNumerals.toRoman(effect.getAmplifier()));
                                     if (!effect.getPotion().isInstant())
                                         line.append(" for ")
                                             .append(effect.getDuration() / 1200) // ticks to mins (/ 20 / 60)
                                             .append(':')
                                             .append(effect.getDuration() / 20 % 60); // to seconds, mod of minute
                                     map.get(category).add(new Pair<>(name, line.toString()));
                                 });

        List<String> list = new ArrayList<>();
        if (wrap)
        {
            linesTotal--;
            int linesNeutral = linesTotal / 5;
            int linesGoodBad = linesTotal - linesNeutral;
            int linesGood, linesBad;
            linesGood = linesBad = linesGoodBad >> 1;
            if (linesGood % 2 != 0) linesGood++;

            int diffGood, diffBad, diffNeutral;
            if ((diffNeutral = linesNeutral - map.get(NEUTRAL).size()) > 0)
                linesNeutral -= diffNeutral;
            if ((diffBad = linesBad - map.get(BAD).size()) > 0)
                linesBad -= diffBad;
            if ((diffGood = linesGood - map.get(GOOD).size()) > 0)
                linesGood -= diffGood;

            final byte goodF = 0b001;
            final byte badF = 0b010;
            final byte neutralF = 0b100;

            for (int i = 0; i < diffNeutral + diffGood + diffBad; i++)
            {
                int flag = (diffGood > 0 ? goodF : 0) | (diffBad > 0 ? badF : 0) | (diffNeutral > 0 ? neutralF : 0);
                if (flag == 0) break;

                if ((flag & (goodF | badF)) == (goodF | badF))
                {
                    flag = i % 2 == 0 ? goodF : badF;
                }
                if ((flag & goodF) > 0)
                {
                    linesGood++;
                    diffGood--;
                }
                else if ((flag & badF) > 0)
                {
                    linesBad++;
                    diffBad--;
                }
                else if ((flag & neutralF) > 0)
                {
                    linesNeutral++;
                    diffNeutral--;
                }
            }

            Iterator<Pair<String, String>> goodIt = map.get(GOOD).iterator();
            for (int i = 0; i < linesGood; i++)
                if (goodIt.hasNext())
                    list.add(goodIt.next().getValue());
                else
                    break;
            Iterator<Pair<String, String>> badIt = map.get(BAD).iterator();
            for (int i = 0; i < linesBad; i++)
                if (badIt.hasNext())
                    list.add(badIt.next().getValue());
                else
                    break;
            Iterator<Pair<String, String>> neutralIt = map.get(NEUTRAL).iterator();
            for (int i = 0; i < linesNeutral; i++)
                if (neutralIt.hasNext())
                    list.add(neutralIt.next().getValue());
                else
                    break;

            list.add(GRAY.toString() + ITALIC
                     + Log.translatef("tooltip.foodPotion.showMore", effects.values().size() - list.size()));
        }
        else
            map.values().forEach(set -> set.forEach(pair -> list.add(pair.getValue())));


        return list;
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
                PotionEffect effect = readEffectFromNBTInASensibleNotMojangManner(tag);
                if (effect != null)
                    effects.put(effect.getPotion(), effect);
            }
        }
        return this;
    }

    private static PotionEffect readEffectFromNBTInASensibleNotMojangManner(NBTTagCompound nbt)
    {
        int id = nbt.getByte("Id") & 0xFF;
        Potion potion = Potion.getPotionById(id);
        if (potion == null) return null;

        // You are reading a 0-255 int that you stored as a signed byte, you have to apply a mask to convert back
        // to the original int value. In vanilla this breaks 127+ amplifiers. Also the formatting was messed up.
        // Seriously just take a look at PotionEffect#readCustomPotionEffectFromNBT and see for yourself.
        int amplifier = nbt.getByte("Amplifier") & 0xFF;
        int duration = nbt.getInteger("Duration");
        boolean ambient = nbt.getBoolean("Ambient");
        boolean particles = !nbt.hasKey("ShowParticles", 1) || nbt.getBoolean("ShowParticles");
        return new PotionEffect(potion, duration, amplifier, ambient, particles);
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

    //FIXME shift clicking into enchantment table gui clears mod tag

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

    public static boolean isEffectEnabled(PotionEffect effect)
    {
        return Conf.FoodPotions.enable
               && effect.getAmplifier() <= Conf.FoodPotions.maxAmplifier
               && effect.getDuration() <= Conf.FoodPotions.maxDuration
               && !Conf.FoodPotions.disabledPotions.contains(effect.getPotion().getRegistryName());
    }

    public static boolean clampArgs(PotionEffect effect)
    {
        if (!Conf.FoodPotions.enable || Conf.FoodPotions.disabledPotions.contains(effect.getPotion().getRegistryName()))
            return true;
        if (effect.getAmplifier() > Conf.FoodPotions.maxAmplifier) effect.amplifier = Conf.FoodPotions.maxAmplifier;
        if (effect.getDuration() > Conf.FoodPotions.maxDuration) effect.duration = Conf.FoodPotions.maxDuration;
        return false;
    }
}
