package it.polimi.ingsw.View.CLI;

/** storage class for visual elements for CLI */

public class AnsiColors {

    public static final String COOL_TITLE = "\n" +
            "      :::::::::: :::::::::  :::::::::::     :::     ::::    ::: ::::::::::: :::   :::  :::::::: \n" +
            "     :+:        :+:    :+:     :+:       :+: :+:   :+:+:   :+:     :+:     :+:   :+: :+:    :+: \n" +
            "    +:+        +:+    +:+     +:+      +:+   +:+  :+:+:+  +:+     +:+      +:+ +:+  +:+         \n" +
            "   +#++:++#   +#++:++#:      +#+     +#++:++#++: +#+ +:+ +#+     +#+       +#++:   +#++:++#++   \n" +
            "  +#+        +#+    +#+     +#+     +#+     +#+ +#+  +#+#+#     +#+        +#+           +#+    \n" +
            " #+#        #+#    #+#     #+#     #+#     #+# #+#   #+#+#     #+#        #+#    #+#    #+#     \n" +
            "########## ###    ### ########### ###     ### ###    ####     ###        ###     ########       \n";
    public static String formatRow(String str)
    {
        return str.replace('|', '\u2502');
    }

    public static String formatDiv(String str)
    {
        return str.replace('a', '\u250c')
                .replace('b', '\u252c')
                .replace('c', '\u2510')
                .replace('d', '\u251c')
                .replace('e', '\u253c')
                .replace('f', '\u2524')
                .replace('g', '\u2514')
                .replace('h', '\u2534')
                .replace('i', '\u2518')
                .replace('-', '\u2500');
    }


}
