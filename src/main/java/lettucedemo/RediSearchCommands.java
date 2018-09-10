package lettucedemo;

import io.lettuce.core.protocol.ProtocolKeyword;

/**
 * @author huishen
 * @date 2018/9/10 下午4:42
 */
public class RediSearchCommands {
    public RediSearchCommands() {
    }

    public interface CommandProvider {
        ProtocolKeyword getCreateCommand();

        ProtocolKeyword getAddCommand();

        ProtocolKeyword getAddHashCommand();

        ProtocolKeyword getDelCommand();

        ProtocolKeyword getInfoCommand();

        ProtocolKeyword getDropCommand();

        ProtocolKeyword getSearchCommand();

        ProtocolKeyword getExplainCommand();

        ProtocolKeyword getGetCommand();

        ProtocolKeyword getAggregateCommand();
    }

    public static class ClusterCommands implements RediSearchCommands.CommandProvider {
        public ClusterCommands() {
        }

        @Override
        public ProtocolKeyword getCreateCommand() {
            return RediSearchCommands.ClusterCommand.CREATE;
        }

        @Override
        public ProtocolKeyword getAddCommand() {
            return RediSearchCommands.ClusterCommand.ADD;
        }

        @Override
        public ProtocolKeyword getAddHashCommand() {
            return RediSearchCommands.ClusterCommand.ADDHASH;
        }

        @Override
        public ProtocolKeyword getDelCommand() {
            return RediSearchCommands.ClusterCommand.DEL;
        }

        @Override
        public ProtocolKeyword getInfoCommand() {
            return RediSearchCommands.ClusterCommand.INFO;
        }

        @Override
        public ProtocolKeyword getDropCommand() {
            return RediSearchCommands.ClusterCommand.DROP;
        }

        @Override
        public ProtocolKeyword getSearchCommand() {
            return RediSearchCommands.ClusterCommand.SEARCH;
        }

        @Override
        public ProtocolKeyword getExplainCommand() {
            return RediSearchCommands.ClusterCommand.EXPLAIN;
        }

        @Override
        public ProtocolKeyword getGetCommand() {
            return RediSearchCommands.ClusterCommand.GET;
        }

        @Override
        public ProtocolKeyword getAggregateCommand() {
            return RediSearchCommands.ClusterCommand.AGGREGATE;
        }
    }

    public static class SingleNodeCommands implements RediSearchCommands.CommandProvider {
        public SingleNodeCommands() {
        }

        @Override
        public ProtocolKeyword getCreateCommand() {
            return RediSearchCommands.Command.CREATE;
        }

        @Override
        public ProtocolKeyword getAddCommand() {
            return RediSearchCommands.Command.ADD;
        }

        @Override
        public ProtocolKeyword getAddHashCommand() {
            return RediSearchCommands.Command.ADDHASH;
        }

        @Override
        public ProtocolKeyword getDelCommand() {
            return RediSearchCommands.Command.DEL;
        }

        @Override
        public ProtocolKeyword getInfoCommand() {
            return RediSearchCommands.Command.INFO;
        }

        @Override
        public ProtocolKeyword getDropCommand() {
            return RediSearchCommands.Command.DROP;
        }

        @Override
        public ProtocolKeyword getSearchCommand() {
            return RediSearchCommands.Command.SEARCH;
        }

        @Override
        public ProtocolKeyword getExplainCommand() {
            return RediSearchCommands.Command.EXPLAIN;
        }

        @Override
        public ProtocolKeyword getGetCommand() {
            return RediSearchCommands.Command.GET;
        }

        @Override
        public ProtocolKeyword getAggregateCommand() {
            return RediSearchCommands.Command.AGGREGATE;
        }
    }

    public enum ClusterCommand implements ProtocolKeyword {
        CREATE("FT.CREATE"),
        ADD("FT.ADD"),
        ADDHASH("FT.ADDHASH"),
        INFO("FT.INFO"),
        SEARCH("FT.FSEARCH"),
        EXPLAIN("FT.EXPLAIN"),
        DEL("FT.DEL"),
        DROP("FT.DROP"),
        BROADCAST("FT.BROADCAST"),
        GET("FT.GET"),
        AGGREGATE("FT.AGGREGATE");

        private final byte[] name;

        private ClusterCommand(String name) {
            this.name = name.getBytes();
        }

        @Override
        public byte[] getBytes() {
            return name;
        }
    }

    public enum Command implements ProtocolKeyword {
        CREATE("FT.CREATE"),
        ADD("FT.ADD"),
        ADDHASH("FT.ADDHASH"),
        INFO("FT.INFO"),
        SEARCH("FT.SEARCH"),
        EXPLAIN("FT.EXPLAIN"),
        DEL("FT.DEL"),
        DROP("FT.DROP"),
        GET("FT.GET"),
        AGGREGATE("AGGREGATE");

        private final byte[] name;

        private Command(String name) {
            this.name = name.getBytes();
        }

        @Override
        public byte[] getBytes() {
            return name;
        }
    }
}
