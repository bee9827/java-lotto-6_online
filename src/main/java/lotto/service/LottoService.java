package lotto.service;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Supplier;
import java.util.stream.Stream;
import lotto.model.Lotto;
import lotto.model.LottoMoney;
import lotto.model.LottoNumberGenerator;
import lotto.model.LottoRank;
import lotto.model.WinningLotto;

public class LottoService {
    private final LottoNumberGenerator lottoNumberGenerator;

    public LottoService(LottoNumberGenerator lottoNumberGenerator) {
        this.lottoNumberGenerator = lottoNumberGenerator;
    }

    public List<Lotto> purchaseTicket(LottoMoney lottoMoney) {
        Supplier<Lotto> createLotto = () ->
                new Lotto(lottoNumberGenerator.getOrderedLottoNumbers());

        return Stream.generate(createLotto)
                .limit(lottoMoney.getPurchaseCount())
                .toList();
    }

    public Map<LottoRank, Long> getRankResults(
            WinningLotto winningLotto,
            List<Lotto> purchasedLotto
    ) {
        return purchasedLotto.stream()
                .collect(groupingBy(lotto ->
                        rankOf(winningLotto, lotto), counting()));
    }

    private LottoRank rankOf(WinningLotto winningLotto, Lotto lotto) {
        return LottoRank.valueOf(
                winningLotto.matchCount(lotto),
                winningLotto.matchBonus(lotto));
    }

    public Double getRevenueRate(Map<LottoRank, Long> winningLottoStatusAndCounts, LottoMoney lottoMoney) {
        Long revenue = getRevenue(winningLottoStatusAndCounts);
        return lottoMoney.getRevenueRate(revenue);
    }

    private Long getRevenue(Map<LottoRank, Long> winningLottoStatusAndCounts) {
        return winningLottoStatusAndCounts.entrySet()
                .stream()
                .map(this::toCost)
                .reduce(Long::sum)
                .orElse(0L);
    }

    private Long toCost(Entry<LottoRank, Long> rankResult) {
        return rankResult.getKey().getPrice() * rankResult.getValue();
    }
}
