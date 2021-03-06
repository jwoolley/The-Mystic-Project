package mysticmod.cards;

import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mysticmod.actions.LoadCardImageAction;
import mysticmod.patches.AbstractCardEnum;
import mysticmod.patches.MysticTags;
import mysticmod.powers.SpellsPlayed;


public class BulkUp extends AbstractAltArtMysticCard {
    public static final String ID = "mysticmod:BulkUp";
    public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String ALTERNATE_IMG_PATH = "mysticmod/images/cards/alternate/bulkup.png";
    private static final int COST = 1;
    private static final int BLOCK_AMT = 6;
    private static final int UPGRADE_EXTRA_BLK = 3;

    public BulkUp() {
        super(ID, NAME, ALTERNATE_IMG_PATH, COST, DESCRIPTION,
                CardType.SKILL, AbstractCardEnum.MYSTIC_PURPLE,
                CardRarity.COMMON, CardTarget.SELF);
        IMG_PATH = "mysticmod/images/cards/bulkup.png";
        loadCardImage(IMG_PATH);
        block = baseBlock = BLOCK_AMT;
        tags.add(MysticTags.IS_ARTE);
        tags.add(MysticTags.IS_POWERFUL);
        altGlowColor = ALT_GLOW_BLUE;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, block));
        if (p.hasPower(SpellsPlayed.POWER_ID)) {
            AbstractDungeon.actionManager.addToBottom(new ExhaustAction(p, p, 1, false, true, true));
        } else {
            AbstractDungeon.actionManager.addToBottom(new ExhaustAction(p, p, 1, true));
        }
        if (isArtAlternate) {
            AbstractDungeon.actionManager.addToBottom(new LoadCardImageAction(this, IMG_PATH, false));
            isArtAlternate = false;
        }
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        if (AbstractDungeon.player.hasPower(SpellsPlayed.POWER_ID)) {
            if (!isArtAlternate) {
                AbstractDungeon.actionManager.addToBottom(new LoadCardImageAction(this, ALTERNATE_IMG_PATH, true));
                isArtAlternate = true;
            }
        } else {
            if (isArtAlternate) {
                loadCardImage(IMG_PATH);
                isArtAlternate = false;
            }
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new BulkUp();
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_EXTRA_BLK);
        }
    }
}
