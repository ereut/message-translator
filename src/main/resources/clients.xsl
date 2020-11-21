<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet
        xmlns:xsl=
                "http://www.w3.org/1999/XSL/Transform"
        version="1.0">

    <xsl:template match="/">
        <xsl:text>
        </xsl:text>
        <xsl:for-each select="List/item">
            <xsl:value-of select="id"/>/<xsl:value-of select="balance"/>
            <xsl:text>
            </xsl:text>
        </xsl:for-each>
    </xsl:template>

</xsl:stylesheet>

