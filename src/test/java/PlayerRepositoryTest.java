import com.depthchart.app.Player;
import com.depthchart.app.PlayerRepository;
import com.depthchart.enums.BasePositionEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PlayerRepositoryTest {
    private PlayerRepository playerRepository;

    @BeforeEach
    public void setUp() {
        playerRepository = new PlayerRepository();
    }

    @ParameterizedTest
    @MethodSource("TestPlayerDataProvider#providePlayers")
    public void addNewPlayer(int playerId, String name, BasePositionEnum position) {
        Player player = new Player(playerId, name, position);
        playerRepository.addPlayer(player);
        assertTrue(playerRepository.containsPlayer(player.getId()));
    }
}
