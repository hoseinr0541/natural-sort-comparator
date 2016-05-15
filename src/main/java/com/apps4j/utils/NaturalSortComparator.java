package com.apps4j.utils;

import java.util.Comparator;

/**
 * Comparator which is used to sort values in the natural order identifying long decimals as a single number. E.g.
 * A223 will be greater then A33. This algorithm was created using 2 different algorithm of comparison. Appropriate text
 * of licences is inserted before the original code.
 */
public class NaturalSortComparator implements Comparator {

    @Override
    public int compare(Object o1, Object o2) {
        if (!(o1 instanceof String) || !(o2 instanceof String)) {
            return 0;
        }
        return compareUnsorted((String) o1, (String) o2);
    }

    private static final short SEQUENCE_TYPE_DIGIT = 1;
    private static final short SEQUENCE_TYPE_STRING = 2;

    /**
     * splits string by chunks where division point is a change from digit to string and vice versa
     *
     * @param s       - basic string
     * @param slength - length of basic string
     * @param marker  - position of the starting point
     * @return chunk of the string from the marker to the next division point
     * @throws IndexOutOfBoundsException in case marker is greater than length of the basic string
     */
    final String getChunk(String s, int slength, int marker) {
        StringBuilder chunk = new StringBuilder();
        int sequenceType = Character.isDigit(s.charAt(marker)) ? SEQUENCE_TYPE_DIGIT : SEQUENCE_TYPE_STRING;

        while (marker < slength) {
            char c = s.charAt(marker);
            boolean isDigit = Character.isDigit(c);

            if (sequenceType == SEQUENCE_TYPE_DIGIT ^ isDigit) {
                return chunk.toString();
            }
            chunk.append(c);
            marker++;
        }
        return chunk.toString();
    }

    /*
      The Alphanum Algorithm is an improved sorting algorithm for strings
      containing numbers.  Instead of sorting numbers in ASCII order like
      a standard sort, this algorithm sorts numbers in numeric order.

      The Alphanum Algorithm is discussed at http://www.DaveKoelle.com


      This library is free software; you can redistribute it and/or
      modify it under the terms of the GNU Lesser General Public
      License as published by the Free Software Foundation; either
      version 2.1 of the License, or any later version.

      This library is distributed in the hope that it will be useful,
      but WITHOUT ANY WARRANTY; without even the implied warranty of
      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
      Lesser General Public License for more details.

      You should have received a copy of the GNU Lesser General Public
      License along with this library; if not, write to the Free Software
      Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
    */
    private int compareUnsorted(String s1, String s2) {
        int thisMarker = 0;
        int thatMarker = 0;
        int s1Length = s1.length();
        int s2Length = s2.length();
        while (thisMarker < s1Length && thatMarker < s2Length) {
            String thisChunk = getChunk(s1, s1Length, thisMarker);
            String thatChunk = getChunk(s2, s2Length, thatMarker);
            int thisChunkLength = thisChunk.length();
            int thatChunkLength = thatChunk.length();

            // If both chunks contain numeric characters, sort them numerically
            int result;
            if (isDigitChunk(thisChunk) && isDigitChunk(thatChunk)) {
                // Simple chunk comparison by length.
                result = thisChunkLength - thatChunkLength;
                if (result != 0) {
                    return result;
                }

                for (int i = 0; i < thisChunkLength; i++) {
                    result = thisChunk.charAt(i) - thatChunk.charAt(i);
                    if (result != 0) {
                        return result;
                    }
                }
            } else {
                result = compareStrings(thisChunk, thatChunk);
                if (result != 0) {
                    return result;
                }
            }
            thisMarker += thisChunkLength;
            thatMarker += thatChunkLength;
        }
        return s1Length - s2Length;
    }

    /*
     NaturalOrderComparator.java -- Perform 'natural order' comparisons of strings in Java.
     Copyright (C) 2003 by Pierre-Luc Paour <natorder@paour.com>

     Based on the C version by Martin Pool, of which this is more or less a straight conversion.
     Copyright (C) 2000 by Martin Pool <mbp@humbug.org.au>

     This software is provided 'as-is', without any express or implied
     warranty.  In no event will the authors be held liable for any damages
     arising from the use of this software.

     Permission is granted to anyone to use this software for any purpose,
     including commercial applications, and to alter it and redistribute it
     freely, subject to the following restrictions:

     1. The origin of this software must not be misrepresented; you must not
     claim that you wrote the original software. If you use this software
     in a product, an acknowledgment in the product documentation would be
     appreciated but is not required.
     2. Altered source versions must be plainly marked as such, and must not be
     misrepresented as being the original software.
     3. This notice may not be removed or altered from any source distribution.
    */
    private static int compareStrings(String a, String b) {
        boolean unresolvedEquality = false;
        int ia = 0, ib = 0;
        int nza = 0, nzb = 0;
        char ca, cb;
        int result;
        while (true) {
            // only count the number of zeroes leading the last number compared
            nza = nzb = 0;
            ca = charAt(a, ia);
            cb = charAt(b, ib);
            char sa = ca;
            char sb = cb;
            if (Character.isUpperCase(ca)) {
                ca = Character.toLowerCase(ca);
            }
            if (Character.isUpperCase(cb)) {
                cb = Character.toLowerCase(cb);
            }
            if (ca == 0 && cb == 0) {
                // The strings compare the same. Perhaps the caller
                // will want to call strcmp to break the tie.
                if (sa == 0 && sb == 0) {
                    int i = nza - nzb;
                    if (i > 0) {
                        return i;
                    }
                    unresolvedEquality = true;
                    break;
                }
            }
            // Lowered characters matched
            if (ca == cb) {
            } else if (ca < cb) {
                return -1;
            } else if (ca > cb) {
                return +1;
            }
            ++ia;
            ++ib;
        }
        if (unresolvedEquality) {
            return a.compareTo(b);
        }
        return 0;
    }

    static char charAt(String s, int i) {
        if (i >= s.length()) {
            return 0;
        } else {
            return s.charAt(i);
        }
    }

    private boolean isDigitChunk(String chunk) {
        return Character.isDigit(chunk.charAt(0));
    }
}
