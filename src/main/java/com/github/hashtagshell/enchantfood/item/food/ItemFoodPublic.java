package com.github.hashtagshell.enchantfood.item.food;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

//Copied from ItemFood because so many things are private and final
public class ItemFoodPublic extends ItemFood
{
    /**
     * Number of ticks to run while 'EnumAction'ing until result.
     */
    public int          itemUseDuration;
    /**
     * The amount this food item heals the player.
     */
    public int          healAmount;
    public float        saturationModifier;
    /**
     * Whether wolves like this food (true for raw and cooked porkchop).
     */
    public boolean      isWolfsFavoriteMeat;
    /**
     * If this field is true, the food can be consumed even if the player don't need to eat.
     */
    public boolean      alwaysEdible;
    /**
     * represents the potion effect that will occurr upon eating this food. Set by setPotionEffect
     */
    public PotionEffect potionId;
    /**
     * probably of the set potion effect occurring
     */
    public float        potionEffectProbability;

    public ItemFoodPublic(int amount, float saturation, boolean isWolfFood)
    {
        super(amount, saturation, isWolfFood);
        this.itemUseDuration = 32;
        this.healAmount = amount;
        this.isWolfsFavoriteMeat = isWolfFood;
        this.saturationModifier = saturation;
    }

    public ItemFoodPublic(int amount, boolean isWolfFood)
    {
        this(amount, 0.6F, isWolfFood);
    }

    /**
     * Called when the player finishes using this Item (E.g. finishes eating.). Not called when the player stops using
     * the Item before the action is complete.
     */
    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving)
    {
        stack.shrink(1);

        if (entityLiving instanceof EntityPlayer)
        {
            EntityPlayer entityplayer = (EntityPlayer) entityLiving;
            entityplayer.getFoodStats().addStats(this, stack);
            worldIn.playSound(null, entityplayer.posX, entityplayer.posY, entityplayer.posZ, SoundEvents.ENTITY_PLAYER_BURP, SoundCategory.PLAYERS, 0.5F, worldIn.rand.nextFloat() * 0.1F + 0.9F);
            this.onFoodEaten(stack, worldIn, entityplayer);
            //noinspection ConstantConditions
            entityplayer.addStat(StatList.getObjectUseStats(this));
        }

        return stack;
    }

    protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player)
    {
        if (!worldIn.isRemote && this.potionId != null && worldIn.rand.nextFloat() < this.potionEffectProbability)
        {
            player.addPotionEffect(new PotionEffect(this.potionId));
        }
    }

    /**
     * How long it takes to use or consume an item
     */
    public int getMaxItemUseDuration(ItemStack stack)
    {
        return 32;
    }

    /**
     * returns the action that specifies what animation to play when the items is being used
     */
    public EnumAction getItemUseAction(ItemStack stack)
    {
        return EnumAction.EAT;
    }

    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        ItemStack itemstack = playerIn.getHeldItem(handIn);

        if (playerIn.canEat(this.alwaysEdible))
        {
            playerIn.setActiveHand(handIn);
            return new ActionResult<>(EnumActionResult.SUCCESS, itemstack);
        } else
        {
            return new ActionResult<>(EnumActionResult.FAIL, itemstack);
        }
    }

    public int getHealAmount(ItemStack stack)
    {
        return this.healAmount;
    }

    public float getSaturationModifier(ItemStack stack)
    {
        return this.saturationModifier;
    }

    /**
     * Whether wolves like this food (true for raw and cooked porkchop).
     */
    public boolean isWolfsFavoriteMeat()
    {
        return this.isWolfsFavoriteMeat;
    }

    public ItemFood setPotionEffect(PotionEffect effect, float probability)
    {
        this.potionId = effect;
        this.potionEffectProbability = probability;
        return this;
    }

    /**
     * Set the field 'alwaysEdible' to true, and make the food edible even if the player don't need to eat.
     */
    public ItemFood setAlwaysEdible()
    {
        this.alwaysEdible = true;
        return this;
    }
}
