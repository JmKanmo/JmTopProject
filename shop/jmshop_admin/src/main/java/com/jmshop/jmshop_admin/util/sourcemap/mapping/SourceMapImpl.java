package com.jmshop.jmshop_admin.util.sourcemap.mapping;

import java.util.ArrayList;
import java.util.List;


public class SourceMapImpl implements SourceMap {
    private State state;

    /**
     * Parse source map.
     * @param sourceMap source map content.
     */
    public SourceMapImpl(String sourceMap) {
        this.state = new Read(this, new SourceMapConsumer(sourceMap));
    }

    /**
     * Create empty source map.
     */
    public SourceMapImpl() {
       this.state = new None(this);
    }

    protected SourceMapImpl(SourceMap sourceMap, int offset) {
        this.state = new DeferredOffset(this, sourceMap, offset);
    }

    @Override
    public void addMapping(int generatedLine, int generatedColumn, int sourceLine, int sourceColumn, String sourceFileName) {
        addMapping(generatedLine, generatedColumn, sourceLine, sourceColumn, sourceFileName, null);
    }

    @Override
    public void addMapping(int generatedLine, int generatedColumn, int sourceLine, int sourceColumn, String sourceFileName, String sourceSymbolName) {
        addMapping(new SourceMappingImpl(generatedLine, generatedColumn, sourceLine, sourceColumn, sourceFileName, sourceSymbolName));
    }

    @Override
    public void addMapping(SourceMapping sourceMapping) {
        state.addMapping(sourceMapping);
    }

    @Override
    public SourceMapping getMapping(int lineNumber, int column) {
        return state.getMapping(lineNumber, column);
    }

    @Override
    public String generate() {
        return state.generate();
    }

    @Override
    public String generateForHumans() {
        SourceMapConsumer sourceMapConsumer = new SourceMapConsumer(generate());
        final StringBuilder buff = new StringBuilder();
        buff.append("{\n");
        buff.append("  sources  : [\n    " + SourceMapInternalUtil.join(sourceMapConsumer.getSourceFileNames(), "\n    ") + "\n  ]\n");
        buff.append("  mappings : [\n    ");
        final int[] previousLine = new int[]{-1};
        sourceMapConsumer.eachMapping(new EachMappingCallback() {
            public void apply(SourceMapping sourceMapping) {
                if ((sourceMapping.getGeneratedLine() != previousLine[0]) && (previousLine[0] != -1)) buff.append("\n    ");
                else if (previousLine[0] != -1) buff.append(", ");
                previousLine[0] = sourceMapping.getGeneratedLine();

                String shortName = sourceMapping.getSourceFileName().replaceAll(".*/", "");
                buff.append("(" + sourceMapping.getGeneratedLine() + ":" + sourceMapping.getGeneratedColumn() + " -> "
                    + shortName + ":" + sourceMapping.getSourceLine() + ":" + sourceMapping.getSourceColumn() + ")");
            }
        });
        buff.append("\n  ]\n}");
        return buff.toString();
    }

    @Override
    public List<String> getSourceFileNames() {
        return state.getSourceFileNames();
    }

    @Override
    public void eachMapping(EachMappingCallback callback) {
        state.eachMapping(callback);
    }

    /**
     * Due to the poor current implementation it's possible to either write or read the source map but not
     * read and write it simultaneously.
     *
     * In order to simplify working with source map its API looks as if it allows simultaneous read and write
     * but it does it by serializing / deserializing the source map to the string.
     *
     * It's implemented as a state machine, switching into Read, Write or DeferredOffset state.
     */
    private static interface State {
        void addMapping(SourceMapping sourceMapping);

        public String generate();

        public void eachMapping(EachMappingCallback callback);

        public SourceMapping getMapping(int lineNumber, int column);

        public List<String> getSourceFileNames();
    }

    /**
     * Initial state, does nothing except of switching into another states.
     */
    private static class None implements State {
        private final SourceMapImpl thisSourceMap;

        public None(SourceMapImpl thisSourceMap) {
            this.thisSourceMap = thisSourceMap;
        }

        @Override
        public void addMapping(SourceMapping sourceMapping) {
            switchIntoWriteState().addMapping(sourceMapping);
        }

        @Override
        public String generate() {
            return switchIntoReadState().generate();
        }

        @Override
        public void eachMapping(EachMappingCallback callback) {
            switchIntoReadState().eachMapping(callback);
        }

        @Override
        public SourceMapping getMapping(int lineNumber, int column) {
            return switchIntoReadState().getMapping(lineNumber, column);
        }

        @Override
        public List<String> getSourceFileNames() {
            return switchIntoReadState().getSourceFileNames();
        }

        private State switchIntoReadState() {
            this.thisSourceMap.state = new Read(thisSourceMap, new SourceMapConsumer(
                "{\n" +
                "  \"version\":3,\n" +
                "  \"sources\":[],\n" +
                "  \"names\":[],\n" +
                "  \"mappings\":\"\"\n" +
                "}"
            ));
            return this.thisSourceMap.state;
        }

        private State switchIntoWriteState() {
            this.thisSourceMap.state = new Write(thisSourceMap);
            return this.thisSourceMap.state;
        }
    }

    /**
     * State of reading source map.
     */
    private static class Read implements State {
        private SourceMapConsumer sourceMapConsumer;
        private SourceMapImpl thisSourceMap;

        public Read(SourceMapImpl thisSourceMap, SourceMapConsumer sourceMapConsumer) {
            this.thisSourceMap = thisSourceMap;
            this.sourceMapConsumer = sourceMapConsumer;
        }

        @Override
        public void eachMapping(final EachMappingCallback callback) {
            sourceMapConsumer.eachMapping(callback);
        }

        @Override
        public SourceMapping getMapping(int lineNumber, int column) {
            return sourceMapConsumer.getMapping(lineNumber, column);
        }

        @Override
        public List<String> getSourceFileNames() {
            return new ArrayList<String>(sourceMapConsumer.getSourceFileNames());
        }

        @Override
        public void addMapping(SourceMapping sourceMapping) {
            throw new RuntimeException("operation getSourceFileNames not supported in " + this.getClass().getSimpleName() + " state!");
        }

        @Override
        public String generate() {
            final SourceMapGenerator sourceMapGenerator = new SourceMapGenerator();
            eachMapping(new EachMappingCallback()
            {
                public void apply(SourceMapping sourceMapping)
                {
                    sourceMapGenerator.addMapping(sourceMapping);
                }
            });
            return sourceMapGenerator.generate();
        }
    }

    /**
     * State of writing source map.
     */
    private static class Write implements State {
        private SourceMapImpl thisSourceMap;
        private SourceMapGenerator sourceMapGenerator;
        private int lastGeneratedLine = 0;
        private int lastGeneratedColumn = 0;

        public Write(SourceMapImpl thisSourceMap) {
            this.thisSourceMap = thisSourceMap;
            this.sourceMapGenerator = new SourceMapGenerator();
        }

        @Override
        public void addMapping(SourceMapping sourceMapping) {
            // In current implementation of generator it's required that lines where added in proper order,
            // checking for it.
            if (lastGeneratedLine > sourceMapping.getGeneratedLine())
                throw new RuntimeException("mappings should be added in a proper order!");
            else if ((lastGeneratedLine == sourceMapping.getGeneratedLine()) && (lastGeneratedColumn > sourceMapping.getGeneratedColumn()))
                throw new RuntimeException("mappings should be added in a proper order!");
            lastGeneratedLine = sourceMapping.getGeneratedLine();
            lastGeneratedColumn = sourceMapping.getGeneratedColumn();

            sourceMapGenerator.addMapping(sourceMapping);
        }

        @Override
        public String generate() {
            return sourceMapGenerator.generate();
        }

        @Override
        public void eachMapping(final EachMappingCallback callback) {
            performanceInefficientSwitchIntoReadState().eachMapping(callback);
        }

        @Override
        public SourceMapping getMapping(int lineNumber, int column) {
            return performanceInefficientSwitchIntoReadState().getMapping(lineNumber, column);
        }

        @Override
        public List<String> getSourceFileNames() {
            return performanceInefficientSwitchIntoReadState().getSourceFileNames();
        }

        // Switching into read mode, no writes would be allowed after this call, it's also
        // not efficient because it requires serialization of source map data to and from string.
        private Read performanceInefficientSwitchIntoReadState() {
            Read read = new Read(thisSourceMap, new SourceMapConsumer(generate()));
            thisSourceMap.state = read;
            return read;
        }
    }

    // The actual offset calculation is deferred in order to improve the performance in case of multiple
    // offset transformations.
    private static class DeferredOffset implements State {
        private final SourceMap sourceMapWithoutOffset;
        private int offset = 0;
        private SourceMapImpl thisSourceMap;

        public DeferredOffset(SourceMapImpl thisSourceMap, SourceMap sourceMapWithoutOffset, int offset) {
            this.thisSourceMap = thisSourceMap;
            this.sourceMapWithoutOffset = sourceMapWithoutOffset;
            this.offset = offset;
        }

        @Override
        public void addMapping(SourceMapping sourceMapping) {
            throw new RuntimeException("operation addMapping not supported in " + this.getClass().getSimpleName() + " state!");
        }

        @Override
        public String generate() {
            return calculateOffsetAndSwitchIntoWriteState().generate();
        }

        @Override
        public void eachMapping(EachMappingCallback callback) {
            calculateOffsetAndSwitchIntoWriteState().eachMapping(callback);
        }

        @Override
        public SourceMapping getMapping(int lineNumber, int column) {
            return calculateOffsetAndSwitchIntoWriteState().getMapping(lineNumber, column);
        }

        @Override
        public List<String> getSourceFileNames() {
            return calculateOffsetAndSwitchIntoWriteState().getSourceFileNames();
        }

        private State calculateOffsetAndSwitchIntoWriteState() {
            final Write write = new Write(thisSourceMap);
            sourceMapWithoutOffset.eachMapping(new EachMappingCallback() {
                public void apply(SourceMapping sourceMapping) {
                    write.addMapping(new SourceMappingImpl(
                        offset + sourceMapping.getGeneratedLine(),
                        sourceMapping.getGeneratedColumn(),
                        sourceMapping.getSourceLine(),
                        sourceMapping.getSourceColumn(),
                        sourceMapping.getSourceFileName(),
                        sourceMapping.getSourceSymbolName()
                    ));
                }
            });
            thisSourceMap.state = write;
            return write;
        }
    }
}