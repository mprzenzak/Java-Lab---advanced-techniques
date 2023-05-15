<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="html"/>
    <xsl:template match="/">
        <html>
            <body>
                <table border="1">
                    <tr>
                        <th>Link</th>
                        <th>ID</th>
                        <th>Data</th>
                        <th>Skrot Organizacja</th>
                        <th>Komponent Srodowiska</th>
                        <th>Typ Karty</th>
                        <th>Rodzaj Karty</th>
                        <th>Nr Wpisu</th>
                        <th>Znak Sprawy</th>
                        <th>Dane Wnioskodawcy</th>
                    </tr>
                    <xsl:for-each select="bip.poznan.pl/data/karty_informacyjne/items/karta_informacyjna">
                        <tr>
                            <td><xsl:value-of select="link"/></td>
                            <td><xsl:value-of select="id"/></td>
                            <td><xsl:value-of select="data"/></td>
                            <td><xsl:value-of select="skrot_organizacja"/></td>
                            <td><xsl:value-of select="komponent_srodowiska"/></td>
                            <td><xsl:value-of select="typ_karty"/></td>
                            <td><xsl:value-of select="rodzaj_karty"/></td>
                            <td><xsl:value-of select="nr_wpisu"/></td>
                            <td><xsl:value-of select="znak_sprawy"/></td>
                            <td><xsl:value-of select="dane_wnioskodawcy"/></td>
                        </tr>
                    </xsl:for-each>
                </table>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>
