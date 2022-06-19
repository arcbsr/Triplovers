package com.arcadio.triplover.utils;

public class Enums {
    public enum CalenderType {
        RETURN,
        DEPART,
        BOTH
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

    public enum Classes {
        Economy(1),
        Premium(2),
        Business(3),
        Fast(4);
        private int index;

        Classes(int text) {
            this.index = text;
        }


        public int getText() {
            return this.index;
        }

        public static Classes fromString(int text) {
            for (Classes b : Classes.values()) {
                if (b.index == text) {
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

    public enum FlightDetailsType {
        DETAILS,
        BAGGAGE,
        PRICE
    }
}
