package com.arcadio.triplover.utils;

public class Enums {
    public enum CalenderType {
        RETURN,
        DEPART
    }

    public enum FlightType {
        ONE_WAY,
        ROUND,
        MULTICITY
    }

    public enum UserAgeType {
        ADULT,
        CHILD,
        INFANT
    }

    public enum Sorting {
        PRICEASC("Price Low"),
        PRICEDESC("Price High"),
        TIMEASC("Time ASC"),
        TIMEDESC("Time DESC");
        private String text;

        Sorting(String text) {
            this.text = text;
        }

        public String getText() {
            return this.text;
        }

        public static Sorting fromString(String text) {
            for (Sorting b : Sorting.values()) {
                if (b.text.equalsIgnoreCase(text)) {
                    return b;
                }
            }
            return null;
        }
    }

    public enum CodeSearchType {
        CountryCodes,
        Countries,
        PhoneCodes
    }
}
