package lotto.io;

import java.util.List;
import java.util.Map;
import lotto.model.Lotto;
import lotto.model.LottoRank;

public interface OutputView {
    void println();
    void printInstruction(String instruction);
    void printLottoList(List<Lotto> lottoList);
    void printWinningStatus(Map<LottoRank, Long> winningLottoStatusAndCounts, Double revenueRate);
    void printError(String error);
}
