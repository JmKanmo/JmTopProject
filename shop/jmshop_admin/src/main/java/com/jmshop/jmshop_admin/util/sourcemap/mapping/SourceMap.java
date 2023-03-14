package com.jmshop.jmshop_admin.util.sourcemap.mapping;

import java.util.List;

/**
 * Reads, writes and combines source maps.
 */
public interface SourceMap {
    /**
     * Add mapping.
     */
    public void addMapping(int generatedLine, int generatedColumn, int sourceLine, int sourceColumn, String sourceFileName);

    /**
     * Add mapping.
     */
    public void addMapping(int generatedLine, int generatedColumn, int sourceLine, int sourceColumn, String sourceFileName, String sourceSymbolName);

    /**
     * Add mapping.
     */
    public void addMapping(SourceMapping sourceMapping);

    /**
     * Get mapping for line and column in generated file.
     */
    public SourceMapping getMapping(int lineNumber, int column);

    /**
     * Generate source map JSON.
     */
    public String generate();

    /**
     * Generate source map in format easily read by humans, for debug purposes.
     */
    public String generateForHumans();

    /**
     * Get list of source file names.
     */
    public List<String> getSourceFileNames();

    public static interface EachMappingCallback {
        public void apply(SourceMapping sourceMapping);
    }

    /**
     * Iterate over mappings.
     */
    public void eachMapping(EachMappingCallback callback);
}