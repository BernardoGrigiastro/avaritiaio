package com.tfar.avaritiaio.capacitor;

import com.enderio.core.client.handlers.SpecialTooltipHandler;
import com.enderio.core.common.CompoundCapabilityProvider;
import com.enderio.core.common.util.NullHelper;
import crazypants.enderio.api.IModObject;
import crazypants.enderio.api.capacitor.CapabilityCapacitorData;
import crazypants.enderio.api.capacitor.ICapacitorData;
import crazypants.enderio.base.EnderIOTab;
import crazypants.enderio.base.lang.Lang;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class ItemAvaritiaIOCapacitor extends Item {
    private @Nonnull ICapacitorData data;

    public static ItemAvaritiaIOCapacitor create_infinity(@Nonnull IModObject modObject) {
        return new ItemAvaritiaIOCapacitor(modObject, AvaritiaIOCapacitorData.INFINITY_CAPACITOR);
    }



    protected ItemAvaritiaIOCapacitor(@Nonnull IModObject modObject, @Nonnull ICapacitorData data) {
        this (modObject, data, 0);
    }

    protected ItemAvaritiaIOCapacitor(@Nonnull IModObject modObject, @Nonnull ICapacitorData data, int damage) {
        setCreativeTab(EnderIOTab.tabEnderIOMaterials);
        modObject.apply(this);
        if (damage <= 0) {
            setMaxStackSize(64);
        }
        setMaxDamage(damage >= 0 ? damage : 0);
        this.data = data;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(@Nonnull ItemStack stack, @Nullable World worldIn, @Nonnull List<String> tooltip, @Nonnull ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(Lang.MACHINE_UPGRADE.get());
        if (SpecialTooltipHandler.showAdvancedTooltips()) {
            SpecialTooltipHandler.addDetailedTooltipFromResources(tooltip, Lang.MACHINE_UPGRADE.getKey());
        } else {
            SpecialTooltipHandler.addShowDetailsTooltip(tooltip);
        }
    }



    @Override
    @Nullable
    public ICapabilityProvider initCapabilities(@Nonnull ItemStack stack, @Nullable NBTTagCompound nbt) {
        ICapabilityProvider capProvider = new ICapabilityProvider() {

            @Override
            public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
                return capability == CapabilityCapacitorData.getCapNN();
            }

            @Override
            @Nullable
            public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
                if (capability == CapabilityCapacitorData.getCapNN()) {
                    return CapabilityCapacitorData.getCapNN().cast(NullHelper.notnullJ(getDataFromStack(stack), "Enum.values() has a null"));
                }
                return null;
            }
        };
        return new CompoundCapabilityProvider(super.initCapabilities(stack, nbt), capProvider);
    }

    @Nonnull
    protected ICapacitorData getDataFromStack(@Nonnull ItemStack stack) {
        return data;
    }

    @Nonnull
    public ICapacitorData getData() {
        return data;
    }
}



