package mysticmod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.RegenPower;
import mysticmod.patches.AbstractCardEnum;
import mysticmod.patches.MysticTags;

public class CureLightWounds extends AbstractMysticCard {
    public static final String ID = "mysticmod:CureLightWounds";
    public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String UPGRADED_NAME = cardStrings.EXTENDED_DESCRIPTION[0];
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String IMG_PATH = "mysticmod/images/cards/curelightwounds.png";
    public static final String ALTERNATE_IMG_PATH = "mysticmod/images/cards/alternate/curemoderatewounds.png";
    private static final int COST = 0;
    private static final int REGEN_AMT = 2;
    private static final int REGEN_UPGRADE_PLUS = 1;

    public CureLightWounds() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                AbstractCard.CardType.SKILL, AbstractCardEnum.MYSTIC_PURPLE,
                AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.SELF);
        magicNumber = baseMagicNumber = REGEN_AMT;
        exhaust = true;
        tags.add(MysticTags.IS_SPELL);
        tags.add(CardTags.HEALING);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new RegenPower(p, magicNumber), magicNumber));
    }

    @Override
    public AbstractCard makeCopy() {
        return new CureLightWounds();
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            name = UPGRADED_NAME;
            loadCardImage(ALTERNATE_IMG_PATH);
            upgradeMagicNumber(REGEN_UPGRADE_PLUS);
        }
    }
}
