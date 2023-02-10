package com.rabbitminers.extendedgears.util;

import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.world.level.block.Block;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Function;

public class CogWheelBlockList<T extends Block> implements Iterable<BlockEntry<T>> {
    private static final int SIZE_AMOUNT = CogWheelType.values().length;

    private final BlockEntry<?>[] values = new BlockEntry<?>[SIZE_AMOUNT];

    public CogWheelBlockList(Function<CogWheelType, BlockEntry<? extends T>> filler) {
        for (CogWheelType type : CogWheelType.values()) {
            values[type.ordinal()] = filler.apply(type);
        }
    }

    @SuppressWarnings("unchecked")
    public BlockEntry<T> get(CogWheelType size) {
        return (BlockEntry<T>) values[size.ordinal()];
    }

    public boolean contains(Block block) {
        for (BlockEntry<?> entry : values) {
            if (entry.is(block)) {
                return true;
            }
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    public BlockEntry<T>[] toArray() {
        return (BlockEntry<T>[]) Arrays.copyOf(values, values.length);
    }

    @Override
    public Iterator<BlockEntry<T>> iterator() {
        return new Iterator<>() {
            private int index = 0;

            @Override
            public boolean hasNext() {
                return index < values.length;
            }

            @SuppressWarnings("unchecked")
            @Override
            public BlockEntry<T> next() {
                if (!hasNext())
                    throw new NoSuchElementException();
                return (BlockEntry<T>) values[index++];
            }
        };
    }
}

