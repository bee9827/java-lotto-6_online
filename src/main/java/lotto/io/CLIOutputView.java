package lotto.io;

import java.util.List;
import java.util.Map;
import lotto.model.Lotto;
import lotto.model.LottoRank;

public class CLIOutputView implements OutputView {
    public static final String ERROR_PREFIX = "[ERROR] ";

    @Override
    public void println() {
        System.out.println();
    }

    @Override
    public void printInstruction(String instruction) {
        System.out.println(instruction);
    }

    @Override
    public void printLottoList(List<Lotto> lottoList) {
        System.out.printf("%d개를 구매했습니다.%n", lottoList.size());
        lottoList.forEach(this::printLotto);
    }

    private void printLotto(Lotto lotto) {
        System.out.println(lotto.toString());
    }

    @Override
    public void printWinningStatus(Map<LottoRank, Long> winningLottoStatusAndCounts, Double revenueRate) {
        System.out.println("당첨 통계");
        System.out.println("---");
        for (LottoRank status : LottoRank.values()) {
            printStatus(status, winningLottoStatusAndCounts.getOrDefault(status, 0L));
        }
        System.out.printf("총 수익률은 %.1f%%입니다.", revenueRate);
    }

    private void printStatus(LottoRank status, Long count) {
        if (status == LottoRank.SECOND) {
            System.out.printf("%d개 일치, 보너스 볼 일치 (%,d원) - %d개%n",
                    status.getMatchCount(), status.getPrice(), count);
            return;
        }
        System.out.printf("%d개 일치 (%,d원) - %,d개%n",
                status.getMatchCount(), status.getPrice(), count);
    }

    @Override
    public void printError(String error) {
        System.out.println(ERROR_PREFIX + error);
    }
}
