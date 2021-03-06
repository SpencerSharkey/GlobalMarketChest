package fr.epicanard.globalmarketchest.gui.actions;

import java.util.function.Consumer;

import fr.epicanard.globalmarketchest.GlobalMarketChest;
import fr.epicanard.globalmarketchest.auctions.AuctionInfo;
import fr.epicanard.globalmarketchest.auctions.AuctionType;
import fr.epicanard.globalmarketchest.gui.InventoryGUI;
import fr.epicanard.globalmarketchest.gui.TransactionKey;
import fr.epicanard.globalmarketchest.shops.ShopInfo;

/**
 * Consumer to create Auction
 */
public class NewAuction implements Consumer<InventoryGUI> {

  @Override
  public void accept(InventoryGUI inv) {
    ShopInfo shop = inv.getTransactionValue(TransactionKey.SHOPINFO);
    Integer maxAuctions = GlobalMarketChest.plugin.getConfigLoader().getConfig().getInt("Options.MaxAuctionByPlayer");

    GlobalMarketChest.plugin.auctionManager.getAuctionNumber(shop.getGroup(), inv.getPlayer(), auctionNumber -> {
      if (auctionNumber >= maxAuctions) {
        inv.getWarn().warn("MaxAuctionByPlayer", 49);
      } else {
        AuctionInfo info = new AuctionInfo(AuctionType.SELL, inv.getPlayer(), shop.getGroup());
        inv.getTransaction().put(TransactionKey.AUCTIONINFO, info);
        inv.loadInterface("CreateAuctionItem");
      }
    });
  }
}