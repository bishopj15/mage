/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package mage.cards.p;

import java.util.UUID;

import mage.constants.CardType;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.permanent.token.Token;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author Loki
 */
public class Pentavus extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("Pentavite");

    static {
        filter.add(new SubtypePredicate("Pentavite"));
    }

    public Pentavus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{7}");
        this.subtype.add("Construct");
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Pentavus enters the battlefield with five +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance(5)),
                "with five +1/+1 counters on it"));        

        // {1}, Remove a +1/+1 counter from Pentavus: Put a 1/1 colorless Pentavite artifact creature token with flying onto the battlefield.
        Ability firstAbility = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new PentaviteToken(), 1), new GenericManaCost(1));
        firstAbility.addCost(new RemoveCountersSourceCost(CounterType.P1P1.createInstance(1)));
        this.addAbility(firstAbility);

        // {1}, Sacrifice a Pentavite: Put a +1/+1 counter on Pentavus.
        Ability secondAbility = new SimpleActivatedAbility(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.P1P1.createInstance(1)), new GenericManaCost(1));
        secondAbility.addCost(new SacrificeTargetCost(new TargetControlledPermanent(filter)));
        this.addAbility(secondAbility);
    }

    public Pentavus(final Pentavus card) {
        super(card);
    }

    @Override
    public Pentavus copy() {
        return new Pentavus(this);
    }
}

class PentaviteToken extends Token {
    public PentaviteToken() {
        super("Pentavite", "1/1 colorless Pentavite artifact creature token with flying");
        cardType.add(CardType.ARTIFACT);
        cardType.add(CardType.CREATURE);
        subtype.add("Pentavite");
        power = new MageInt(1);
        toughness = new MageInt(1);
        this.addAbility(FlyingAbility.getInstance());
    }

}