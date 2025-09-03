package lotto.controller;

import java.util.List;
import java.util.Map;
import lotto.io.InputView;
import lotto.io.OutputView;
import lotto.model.Lotto;
import lotto.model.LottoMoney;
import lotto.model.LottoRank;
import lotto.model.WinningLotto;
import lotto.service.LottoService;

public class Controller {
    private final InputView inputView;
    private final OutputView outputView;
    private final LottoService lottoService;

    public Controller(InputView inputView, OutputView outputView, LottoService lottoService) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.lottoService = lottoService;
    }

    public void run() {
        try {
            LottoMoney lottoMoney = lottoMoneyControl(inputView.readCost());
            List<Lotto> lottoList = purchaseControl(lottoMoney);

            WinningLotto winningLotto = winningLottoControl(
                    inputView.readWinningNumbers(), inputView.readBonusNumber());

            statusControl(winningLotto, lottoList, lottoMoney);
        } catch (IllegalArgumentException e) {
            outputView.printError(e.getMessage());
        }
    }

    private WinningLotto winningLottoControl(List<Integer> numbers, Integer bonusNumber) {
        Lotto lotto = new Lotto(numbers);
        outputView.println();

        WinningLotto winningLotto = new WinningLotto(lotto, bonusNumber);
        outputView.println();
        return winningLotto;
    }

    private LottoMoney lottoMoneyControl(Integer cost) {
        LottoMoney lottoMoney = new LottoMoney(cost);
        outputView.println();
        return lottoMoney;
    }

    private List<Lotto> purchaseControl(LottoMoney lottoMoney) {
        List<Lotto> lottoList = lottoService.purchaseTicket(lottoMoney);
        outputView.printLottoList(lottoList);
        outputView.println();
        return lottoList;
    }

    private void statusControl(WinningLotto winningLotto, List<Lotto> lottoList, LottoMoney lottoMoney) {
        Map<LottoRank, Long> rankResults =
                lottoService.getRankResults(winningLotto, lottoList);
        Double revenueRate = lottoService.getRevenueRate(rankResults, lottoMoney);
        outputView.printWinningStatus(rankResults, revenueRate);
    }
}
