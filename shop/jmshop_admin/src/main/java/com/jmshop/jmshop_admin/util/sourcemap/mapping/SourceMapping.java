package com.jmshop.jmshop_admin.util.sourcemap.mapping;

/**
 * Mapping of position from generated file to source file.
 */
public interface SourceMapping
{
    public int getGeneratedLine();

    public int getGeneratedColumn();

    public int getSourceLine();

    public int getSourceColumn();

    public String getSourceFileName();

    public String getSourceSymbolName();
}