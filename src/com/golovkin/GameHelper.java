package com.golovkin;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GameHelper {
    private static Comparator<Integer> nullsLastComparator;

    public GameHelper()
    {
        nullsLastComparator = Comparator.nullsLast(null);
    }

    public List<Integer> moveAndMergeEqual(List<Integer> list) {
        moveElementsToBeginning(list);
        List<Integer> dest = getMergedElements(list);
        ensureEqualSize(list, dest);

        return dest;
    }

    private static void moveElementsToBeginning(List<Integer> list)
    {
        list.sort(nullsLastComparator);
    }

    private static List<Integer> getMergedElements(List<Integer> source) {
        List<Integer> dest = new ArrayList<>();

        int size = getElementCount(source);

        for (int i = 0; i < size; i++)
        {
            if (i != source.size() - 1 && source.get(i).equals(source.get(i + 1))) {
                dest.add(source.get(i) * 2);
                i += 1;
            }
            else {
                dest.add(source.get(i));
            }
        }

        return dest;
    }

    private static int getElementCount(List<Integer> source) {
        if (source.contains(null))
        {
            return source.indexOf(null);
        }
        else {
            return source.size();
        }
    }

    private static void ensureEqualSize(List<Integer> source, List<Integer> dest) {
        while (dest.size() != source.size())
        {
            dest.add(null);
        }
    }
}
