package com.github.hashtagshell.enchantfood.block;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IRotatable {
    enum RotationDirection {
        CLOCKWISE,
        COUNTERCLOCKWISE
    }

    enum FacingType {
        HORIZONTAL,
        ALL
    }

    default FacingType getFacingType() {
        return FacingType.HORIZONTAL;
    }

    EnumFacing getRotation(World w, BlockPos pos);

    void setFacing(World w, BlockPos pos, EnumFacing facing);

    default EnumFacing rotate(World w, BlockPos pos, RotationDirection rotationDirection) {
        switch (this.getFacingType()) {
            case HORIZONTAL:
                int faceHorizontalIndex = getRotation(w, pos).getHorizontalIndex();
                switch (rotationDirection) {
                    case CLOCKWISE: {
                        if (faceHorizontalIndex == EnumFacing.HORIZONTALS.length - 1) {
                            EnumFacing finalHorizontalValueCV = EnumFacing.HORIZONTALS[0];
                            setFacing(w, pos, finalHorizontalValueCV);
                            return finalHorizontalValueCV;
                        }
                        EnumFacing finalHorizontalValueCV = EnumFacing.HORIZONTALS[faceHorizontalIndex + 1];
                        setFacing(w, pos, finalHorizontalValueCV);
                        return finalHorizontalValueCV;
                    }
                    case COUNTERCLOCKWISE: {
                        if (faceHorizontalIndex == 0) {
                            EnumFacing finalHorizontalValueCCV = EnumFacing.HORIZONTALS[EnumFacing.HORIZONTALS.length - 1];
                            setFacing(w, pos, finalHorizontalValueCCV);
                            return finalHorizontalValueCCV;
                        }
                        EnumFacing finalHorizontalValueCCV = EnumFacing.HORIZONTALS[faceHorizontalIndex - 1];
                        setFacing(w, pos, finalHorizontalValueCCV);
                        return finalHorizontalValueCCV;
                    }

                }
            case ALL:
                int faceIndex = getRotation(w, pos).getIndex();
                switch (rotationDirection) {
                    case CLOCKWISE: {
                        if (faceIndex == EnumFacing.VALUES.length - 1) {
                            EnumFacing finalValueCV = EnumFacing.VALUES[0];
                            setFacing(w, pos, finalValueCV);
                            return finalValueCV;
                        }
                        EnumFacing finalValueCV = EnumFacing.VALUES[faceIndex + 1];
                        setFacing(w, pos, finalValueCV);
                        return finalValueCV;
                    }
                    case COUNTERCLOCKWISE: {
                        if (faceIndex == 0) {
                            EnumFacing faceIndexCCV = EnumFacing.VALUES[EnumFacing.VALUES.length - 1];
                            setFacing(w, pos, faceIndexCCV);
                            return faceIndexCCV;
                        }
                        EnumFacing finalHorizontalValueCCV = EnumFacing.VALUES[faceIndex - 1];
                        setFacing(w, pos, finalHorizontalValueCCV);
                        return finalHorizontalValueCCV;
                    }

                }
            default: {
                return EnumFacing.SOUTH;
            }
        }
    }
}
