import com.depthchart.enums.MLBPositionEnum;
import com.depthchart.enums.NFLPositionEnum;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class TestPlayerDataProvider {
    public static Stream<Arguments> providePlayers() {
        return Stream.of(
                Arguments.of(1, "Bob", MLBPositionEnum.P_SS),
                Arguments.of(200, "Jim", NFLPositionEnum.KR),
                Arguments.of(100, "Alex", NFLPositionEnum.WR)
        );
    }
}
