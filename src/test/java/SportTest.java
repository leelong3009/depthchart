import com.depthchart.app.AbstractSport;
import com.depthchart.app.Player;
import com.depthchart.app.PlayerRepository;
import com.depthchart.enums.BasePositionEnum;
import com.depthchart.enums.MLBPositionEnum;
import com.depthchart.enums.NFLPositionEnum;
import com.depthchart.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SportTest {
    private AbstractSport sport;
    private PlayerRepository playerRepository;

    @BeforeEach
    public void setUp() {
        playerRepository = new PlayerRepository();
        sport = new AbstractSport(playerRepository);
    }

    @ParameterizedTest
    @MethodSource("TestPlayerDataProvider#providePlayers")
    public void add1PlayerToDepthChart(int playerId, String name, BasePositionEnum position) {
        // given
        Player player1 = new Player(playerId, name, position);
        playerRepository.addPlayer(player1);
        // when
        sport.addPlayerToDepthChart(player1.getId(), position, 0);
        // then
        assertEquals(0, sport.getPlayerPosition(player1.getId(), position));
    }

    @Test
    public void add2PlayersToDepthChart() {
        //given
        Player player1 = new Player(1, "Bob", NFLPositionEnum.WR);
        Player player2 = new Player(2, "Sam", NFLPositionEnum.WR);
        playerRepository.addPlayer(player1);
        playerRepository.addPlayer(player2);
        // when
        sport.addPlayerToDepthChart(player1.getId(), NFLPositionEnum.WR, 0);
        sport.addPlayerToDepthChart(player2.getId(), NFLPositionEnum.WR, 0);
        // then
        assertEquals(1, sport.getPlayerPosition(player1.getId(), NFLPositionEnum.WR));
        assertEquals(0, sport.getPlayerPosition(player2.getId(), NFLPositionEnum.WR));
    }

    @Test
    public void add3PlayersToDepthChart() {
        //given
        Player player1 = new Player(1, "Bob", NFLPositionEnum.WR);
        Player player2 = new Player(2, "Sam", NFLPositionEnum.WR);
        Player player3 = new Player(3, "Alice", NFLPositionEnum.WR);
        playerRepository.addPlayer(player1);
        playerRepository.addPlayer(player2);
        playerRepository.addPlayer(player3);
        // when
        sport.addPlayerToDepthChart(player1.getId(), NFLPositionEnum.WR, 0);
        sport.addPlayerToDepthChart(player2.getId(), NFLPositionEnum.WR, 0);
        sport.addPlayerToDepthChart(player3.getId(), NFLPositionEnum.WR, 2);
        // then
        assertEquals(1, sport.getPlayerPosition(player1.getId(), NFLPositionEnum.WR));
        assertEquals(0, sport.getPlayerPosition(player2.getId(), NFLPositionEnum.WR));
        assertEquals(2, sport.getPlayerPosition(player3.getId(), NFLPositionEnum.WR));
    }

    @Test
    public void add1PlayerWithPositionOutsideOfDepthChart() {
        //given
        Player player1 = new Player(1, "Bob", NFLPositionEnum.WR);
        playerRepository.addPlayer(player1);
        // then
        assertThrows(ResourceNotFoundException.class, () -> sport.addPlayerToDepthChart(player1.getId(), NFLPositionEnum.K, 1));
    }

    @Test
    public void add2PlayersWithPositionOutsideOfDepthChart() {
        //given
        Player player1 = new Player(1, "Bob", NFLPositionEnum.WR);
        Player player2 = new Player(2, "Sam", NFLPositionEnum.WR);
        playerRepository.addPlayer(player1);
        playerRepository.addPlayer(player2);
        sport.addPlayerToDepthChart(player1.getId(), NFLPositionEnum.K, 0);
        // then
        assertThrows(ResourceNotFoundException.class, () -> sport.addPlayerToDepthChart(player2.getId(), NFLPositionEnum.K, 2));
    }

    @Test
    public void addExistingPlayerToChart_thenRemoveAndAdd() {
        //given
        Player player1 = new Player(1, "Bob", NFLPositionEnum.WR);
        Player player2 = new Player(2, "Sam", NFLPositionEnum.WR);
        Player player3 = new Player(3, "Alice", NFLPositionEnum.WR);
        playerRepository.addPlayer(player1);
        playerRepository.addPlayer(player2);
        playerRepository.addPlayer(player3);
        //when
        sport.addPlayerToDepthChart(player1.getId(), NFLPositionEnum.WR, 0);
        sport.addPlayerToDepthChart(player2.getId(), NFLPositionEnum.WR, 1);
        sport.addPlayerToDepthChart(player3.getId(), NFLPositionEnum.WR, 2);
        sport.addPlayerToDepthChart(player3.getId(), NFLPositionEnum.WR, 1);
        //then
        assertEquals(0, sport.getPlayerPosition(player1.getId(), NFLPositionEnum.WR));
        assertEquals(2, sport.getPlayerPosition(player2.getId(), NFLPositionEnum.WR));
        assertEquals(1, sport.getPlayerPosition(player3.getId(), NFLPositionEnum.WR));
    }

    @Test
    public void add3PlayersWithoutDepthPosition() {
        //given
        Player player1 = new Player(1, "Bob", NFLPositionEnum.WR);
        Player player2 = new Player(2, "Sam", NFLPositionEnum.WR);
        Player player3 = new Player(3, "Alice", NFLPositionEnum.WR);
        playerRepository.addPlayer(player1);
        playerRepository.addPlayer(player2);
        playerRepository.addPlayer(player3);
        // when
        sport.addPlayerToDepthChart(player1.getId(), NFLPositionEnum.WR);
        sport.addPlayerToDepthChart(player2.getId(), NFLPositionEnum.WR);
        sport.addPlayerToDepthChart(player3.getId(), NFLPositionEnum.WR);
        //then
        assertEquals("WR: [1, 2, 3]", sport.getFullDepthChart());
    }

    @Test
    public void add3Players_thenGetPlayersUnderInChart() {
        //given
        Player player1 = new Player(1, "Bob", NFLPositionEnum.WR);
        Player player2 = new Player(2, "Sam", NFLPositionEnum.WR);
        Player player3 = new Player(3, "Alice", NFLPositionEnum.WR);
        playerRepository.addPlayer(player1);
        playerRepository.addPlayer(player2);
        playerRepository.addPlayer(player3);
        sport.addPlayerToDepthChart(player1.getId(), NFLPositionEnum.WR, 0);
        sport.addPlayerToDepthChart(player2.getId(), NFLPositionEnum.WR, 1);
        sport.addPlayerToDepthChart(player3.getId(), NFLPositionEnum.WR, 2);
        // when
        String underPlayer1PlayerIds = sport.getPlayersUnderPlayerInDepthChart(1, NFLPositionEnum.WR);
        String underPlayer2PlayerIds = sport.getPlayersUnderPlayerInDepthChart(2, NFLPositionEnum.WR);
        String underPlayer3PlayerIds = sport.getPlayersUnderPlayerInDepthChart(3, NFLPositionEnum.WR);
        // then
        assertEquals("[2,3]", underPlayer1PlayerIds);
        assertEquals("[3]", underPlayer2PlayerIds);
        assertEquals("[]", underPlayer3PlayerIds);
    }

    @Test
    public void addPlayersToDifferentPositions() {
        //given
        Player player1 = new Player(1, "Bob", MLBPositionEnum.P_SP);
        playerRepository.addPlayer(player1);
        // when
        sport.addPlayerToDepthChart(player1.getId(), NFLPositionEnum.WR, 0);
        sport.addPlayerToDepthChart(player1.getId(), MLBPositionEnum.P_1B, 0);
        // then
        assertEquals(0, sport.getPlayerPosition(player1.getId(), NFLPositionEnum.WR));
        assertEquals(0, sport.getPlayerPosition(player1.getId(), MLBPositionEnum.P_1B));
    }

    @Test
    public void given3PlayersInChart_thenPrintAll() {
        //given
        Player player1 = new Player(1, "Bob", MLBPositionEnum.P_SS);
        Player player2 = new Player(2, "Sam", MLBPositionEnum.P_CF);
        Player player3 = new Player(3, "Alice", MLBPositionEnum.P_1B);
        playerRepository.addPlayer(player1);
        playerRepository.addPlayer(player2);
        playerRepository.addPlayer(player3);
        //when
        sport.addPlayerToDepthChart(player1.getId(), MLBPositionEnum.P_SS, 0);
        sport.addPlayerToDepthChart(player2.getId(), MLBPositionEnum.P_SS, 1);
        sport.addPlayerToDepthChart(player3.getId(), MLBPositionEnum.P_SS, 2);
        //then
        assertEquals("SS: [1, 2, 3]", sport.getFullDepthChart());
    }

}
