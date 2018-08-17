package mysticmod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import mysticmod.patches.AbstractCardEnum;
import mysticmod.powers.SpellsPlayed;
import mysticmod.powers.TechniquesPlayed;

import basemod.abstracts.CustomCard;

public class Flourish
        extends CustomCard {
    public static final String ID = "mysticmod:Flourish";
    public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String IMG_PATH = "mysticmod/images/cards/flourish.png";
    private static final int COST = 4;
    public static final int ATTACK_DMG = 6;
    private static final int UPGRADE_PLUS_DMG = 2;

    public Flourish() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                AbstractCard.CardType.ATTACK, AbstractCardEnum.MYSTIC_PURPLE,
                AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.ENEMY);
        this.damage=this.baseDamage = ATTACK_DMG;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //deal !D! twice
        AbstractDungeon.actionManager.addToBottom(
                new com.megacrit.cardcrawl.actions.common.DamageAction(
                        m, new DamageInfo(p, this.damage, this.damageTypeForTurn)
                        , AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        AbstractDungeon.actionManager.addToBottom(
                new com.megacrit.cardcrawl.actions.common.DamageAction(
                        m, new DamageInfo(p, this.damage, this.damageTypeForTurn)
                        , AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        //double spell/techniques played
        if (p.hasPower(SpellsPlayed.POWER_ID)) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new SpellsPlayed(p, p.getPower(SpellsPlayed.POWER_ID).amount), p.getPower(SpellsPlayed.POWER_ID).amount));
        }
        if (p.hasPower(TechniquesPlayed.POWER_ID)) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new TechniquesPlayed(p, p.getPower(TechniquesPlayed.POWER_ID).amount), p.getPower(TechniquesPlayed.POWER_ID).amount));
        }
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        int reductionAmount = 0;
        if (AbstractDungeon.player.hasPower(SpellsPlayed.POWER_ID)) {
            reductionAmount += AbstractDungeon.player.getPower(SpellsPlayed.POWER_ID).amount;
        }
        if (AbstractDungeon.player.hasPower(TechniquesPlayed.POWER_ID)) {
            reductionAmount += AbstractDungeon.player.getPower(TechniquesPlayed.POWER_ID).amount;
        }
        this.setCostForTurn(this.cost - reductionAmount);
    }

    @Override
    public void triggerWhenDrawn() {
        super.triggerWhenDrawn();
        int reductionAmount = 0;
        if (AbstractDungeon.player.hasPower(SpellsPlayed.POWER_ID)) {
            reductionAmount += AbstractDungeon.player.getPower(SpellsPlayed.POWER_ID).amount;
        }
        if (AbstractDungeon.player.hasPower(TechniquesPlayed.POWER_ID)) {
            reductionAmount += AbstractDungeon.player.getPower(TechniquesPlayed.POWER_ID).amount;
        }
        this.setCostForTurn(this.cost - reductionAmount);
    }

    @Override
    public void triggerWhenCopied() {
        super.triggerWhenDrawn();
        int reductionAmount = 0;
        if (AbstractDungeon.player.hasPower(SpellsPlayed.POWER_ID)) {
            reductionAmount += AbstractDungeon.player.getPower(SpellsPlayed.POWER_ID).amount;
        }
        if (AbstractDungeon.player.hasPower(TechniquesPlayed.POWER_ID)) {
            reductionAmount += AbstractDungeon.player.getPower(TechniquesPlayed.POWER_ID).amount;
        }
        this.setCostForTurn(this.cost - reductionAmount);
    }

    @Override
    public AbstractCard makeCopy() {
        return new Flourish();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(UPGRADE_PLUS_DMG);
        }
    }
}