package com.arcadio.triplover.utils;

import android.app.Activity;
import android.content.Context;
import android.telephony.TelephonyManager;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class CountryToPhonePrefix {
    public static Map<String, String> map = new HashMap<>();

    public static String prefixFor(String iso2CountryCode) {
        if (map.containsKey(iso2CountryCode)) {
            return map.get(iso2CountryCode);
        }
        return "";
    }

    public static class CountryDetails {
        public String countryName = "";
        public String countryCode = "";
        public String phoneCode = "";
        public boolean isSelected = false;

        public CountryDetails(String countryName, String countryCode, String phoneCode, boolean isSelected) {
            this.countryName = countryName;
            this.countryCode = countryCode;
            this.phoneCode = phoneCode;
            this.isSelected = isSelected;
        }
    }

    private static HashMap<String, CountryDetails> countries = new HashMap<>();

    public static HashMap<String, CountryDetails> CountryCodesBuilder(String defaultCode) {
        if (defaultCode == null) {
            defaultCode = "";
        }
        if (countries.size() == 0) {
            for (String iso : Locale.getISOCountries()) {
                Locale l = new Locale("", iso);
                countries.put(l.getCountry(), new CountryDetails(l.getDisplayName(), l.getCountry(), prefixFor(l.getCountry()),
                        (iso.equalsIgnoreCase(defaultCode) ? true : false)));
            }
        }
        return countries;
    }

    public static CountryDetails CountryCodeBuilder(String defaultCode) {
        if (defaultCode == null || defaultCode.isEmpty()) {
            defaultCode = "";
        }
        defaultCode = defaultCode.toUpperCase();
        if (countries.size() == 0) {
            for (String iso : Locale.getISOCountries()) {
                Locale l = new Locale("", iso);
                countries.put(l.getCountry(), new CountryDetails(l.getDisplayName(), l.getCountry(), prefixFor(l.getCountry()),
                        (iso.equalsIgnoreCase(defaultCode) ? true : false)));
            }
        }
        KLog.w(defaultCode);
        CountryDetails countryDetails = new CountryDetails("", "", "", false);
        if (countries.containsKey(defaultCode)) {
            countryDetails = countries.get(defaultCode);
        }
        return countryDetails;
    }

    public static String SearchCountryCodeBuilder(String defaultCode, Enums.CodeSearchType codeSearchType) {
        if (defaultCode == null || defaultCode.isEmpty()) {
            return "";
        }
        if (countries.size() == 0) {
            for (String iso : Locale.getISOCountries()) {
                Locale l = new Locale("", iso);
                countries.put(l.getCountry(), new CountryDetails(l.getDisplayName(), l.getCountry(), prefixFor(l.getCountry()),
                        (iso.equalsIgnoreCase(defaultCode) ? true : false)));

            }
        }
        for (String iso : countries.keySet()) {
            switch (codeSearchType) {
                case Countries:
                    if ((defaultCode.equalsIgnoreCase(countries.get(iso).countryName))) {
                        return countries.get(iso).countryName;
                    }
                    break;
                case PhoneCodes:
                    if ((defaultCode.equalsIgnoreCase(countries.get(iso).phoneCode))) {
                        return countries.get(iso).phoneCode;
                    }
                    break;
                case CountryCodes:
                    if ((defaultCode.equalsIgnoreCase(countries.get(iso).countryCode))) {
                        return countries.get(iso).countryCode;
                    }
                    break;
            }

        }
        return "";
    }

    public static String getLocalCode(Activity context) {
        TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String code = manager.getNetworkCountryIso().toUpperCase();
        return code;
    }

    static {
        map.put("AC", "+247");
        map.put("AD", "+376");
        map.put("AE", "+971");
        map.put("AF", "+93");
        map.put("AG", "+1-268");
        map.put("AI", "+1-264");
        map.put("AL", "+355");
        map.put("AM", "+374");
        map.put("AN", "+599");
        map.put("AO", "+244");
        map.put("AR", "+54");
        map.put("AS", "+1-684");
        map.put("AT", "+43");
        map.put("AU", "+61");
        map.put("AW", "+297");
        map.put("AX", "+358-18");
        map.put("AZ", "+374-97");
        map.put("AZ", "+994");
        map.put("BA", "+387");
        map.put("BB", "+1-246");
        map.put("BD", "+880");
        map.put("BE", "+32");
        map.put("BF", "+226");
        map.put("BG", "+359");
        map.put("BH", "+973");
        map.put("BI", "+257");
        map.put("BJ", "+229");
        map.put("BM", "+1-441");
        map.put("BN", "+673");
        map.put("BO", "+591");
        map.put("BR", "+55");
        map.put("BS", "+1-242");
        map.put("BT", "+975");
        map.put("BW", "+267");
        map.put("BY", "+375");
        map.put("BZ", "+501");
        map.put("CA", "+1");
        map.put("CC", "+61");
        map.put("CD", "+243");
        map.put("CF", "+236");
        map.put("CG", "+242");
        map.put("CH", "+41");
        map.put("CI", "+225");
        map.put("CK", "+682");
        map.put("CL", "+56");
        map.put("CM", "+237");
        map.put("CN", "+86");
        map.put("CO", "+57");
        map.put("CR", "+506");
        map.put("CS", "+381");
        map.put("CU", "+53");
        map.put("CV", "+238");
        map.put("CX", "+61");
        map.put("CY", "+90-392");
        map.put("CY", "+357");
        map.put("CZ", "+420");
        map.put("DE", "+49");
        map.put("DJ", "+253");
        map.put("DK", "+45");
        map.put("DM", "+1-767");
        map.put("DO", "+1-809"); // and 1-829?
        map.put("DZ", "+213");
        map.put("EC", "+593");
        map.put("EE", "+372");
        map.put("EG", "+20");
        map.put("EH", "+212");
        map.put("ER", "+291");
        map.put("ES", "+34");
        map.put("ET", "+251");
        map.put("FI", "+358");
        map.put("FJ", "+679");
        map.put("FK", "+500");
        map.put("FM", "+691");
        map.put("FO", "+298");
        map.put("FR", "+33");
        map.put("GA", "+241");
        map.put("GB", "+44");
        map.put("GD", "+1-473");
        map.put("GE", "+995");
        map.put("GF", "+594");
        map.put("GG", "+44");
        map.put("GH", "+233");
        map.put("GI", "+350");
        map.put("GL", "+299");
        map.put("GM", "+220");
        map.put("GN", "+224");
        map.put("GP", "+590");
        map.put("GQ", "+240");
        map.put("GR", "+30");
        map.put("GT", "+502");
        map.put("GU", "+1-671");
        map.put("GW", "+245");
        map.put("GY", "+592");
        map.put("HK", "+852");
        map.put("HN", "+504");
        map.put("HR", "+385");
        map.put("HT", "+509");
        map.put("HU", "+36");
        map.put("ID", "+62");
        map.put("IE", "+353");
        map.put("IL", "+972");
        map.put("IM", "+44");
        map.put("IN", "+91");
        map.put("IO", "+246");
        map.put("IQ", "+964");
        map.put("IR", "+98");
        map.put("IS", "+354");
        map.put("IT", "+39");
        map.put("JE", "+44");
        map.put("JM", "+1-876");
        map.put("JO", "+962");
        map.put("JP", "+81");
        map.put("KE", "+254");
        map.put("KG", "+996");
        map.put("KH", "+855");
        map.put("KI", "+686");
        map.put("KM", "+269");
        map.put("KN", "+1-869");
        map.put("KP", "+850");
        map.put("KR", "+82");
        map.put("KW", "+965");
        map.put("KY", "+1-345");
        map.put("KZ", "+7");
        map.put("LA", "+856");
        map.put("LB", "+961");
        map.put("LC", "+1-758");
        map.put("LI", "+423");
        map.put("LK", "+94");
        map.put("LR", "+231");
        map.put("LS", "+266");
        map.put("LT", "+370");
        map.put("LU", "+352");
        map.put("LV", "+371");
        map.put("LY", "+218");
        map.put("MA", "+212");
        map.put("MC", "+377");
        map.put("MD", "+373-533");
        map.put("MD", "+373");
        map.put("ME", "+382");
        map.put("MG", "+261");
        map.put("MH", "+692");
        map.put("MK", "+389");
        map.put("ML", "+223");
        map.put("MM", "+95");
        map.put("MN", "+976");
        map.put("MO", "+853");
        map.put("MP", "+1-670");
        map.put("MQ", "+596");
        map.put("MR", "+222");
        map.put("MS", "+1-664");
        map.put("MT", "+356");
        map.put("MU", "+230");
        map.put("MV", "+960");
        map.put("MW", "+265");
        map.put("MX", "+52");
        map.put("MY", "+60");
        map.put("MZ", "+258");
        map.put("NA", "+264");
        map.put("NC", "+687");
        map.put("NE", "+227");
        map.put("NF", "+672");
        map.put("NG", "+234");
        map.put("NI", "+505");
        map.put("NL", "+31");
        map.put("NO", "+47");
        map.put("NP", "+977");
        map.put("NR", "+674");
        map.put("NU", "+683");
        map.put("NZ", "+64");
        map.put("OM", "+968");
        map.put("PA", "+507");
        map.put("PE", "+51");
        map.put("PF", "+689");
        map.put("PG", "+675");
        map.put("PH", "+63");
        map.put("PK", "+92");
        map.put("PL", "+48");
        map.put("PM", "+508");
        map.put("PR", "+1-787"); // and 1-939 ?
        map.put("PS", "+970");
        map.put("PT", "+351");
        map.put("PW", "+680");
        map.put("PY", "+595");
        map.put("QA", "+974");
        map.put("RE", "+262");
        map.put("RO", "+40");
        map.put("RS", "+381");
        map.put("RU", "+7");
        map.put("RW", "+250");
        map.put("SA", "+966");
        map.put("SB", "+677");
        map.put("SC", "+248");
        map.put("SD", "+249");
        map.put("SE", "+46");
        map.put("SG", "+65");
        map.put("SH", "+290");
        map.put("SI", "+386");
        map.put("SJ", "+47");
        map.put("SK", "+421");
        map.put("SL", "+232");
        map.put("SM", "+378");
        map.put("SN", "+221");
        map.put("SO", "+252");
        map.put("SO", "+252");
        map.put("SR", "+597");
        map.put("ST", "+239");
        map.put("SV", "+503");
        map.put("SY", "+963");
        map.put("SZ", "+268");
        map.put("TA", "+290");
        map.put("TC", "+1-649");
        map.put("TD", "+235");
        map.put("TG", "+228");
        map.put("TH", "+66");
        map.put("TJ", "+992");
        map.put("TK", "+690");
        map.put("TL", "+670");
        map.put("TM", "+993");
        map.put("TN", "+216");
        map.put("TO", "+676");
        map.put("TR", "+90");
        map.put("TT", "+1-868");
        map.put("TV", "+688");
        map.put("TW", "+886");
        map.put("TZ", "+255");
        map.put("UA", "+380");
        map.put("UG", "+256");
        map.put("US", "+1");
        map.put("UY", "+598");
        map.put("UZ", "+998");
        map.put("VA", "+379");
        map.put("VC", "+1-784");
        map.put("VE", "+58");
        map.put("VG", "+1-284");
        map.put("VI", "+1-340");
        map.put("VN", "+84");
        map.put("VU", "+678");
        map.put("WF", "+681");
        map.put("WS", "+685");
        map.put("YE", "+967");
        map.put("YT", "+262");
        map.put("ZA", "+27");
        map.put("ZM", "+260");
        map.put("ZW", "+263");
    }
}