package de.hhu.stups.plues.modelgenerator.twig;

import de.hhu.stups.plues.data.entities.AbstractUnit;
import de.hhu.stups.plues.data.entities.Unit;
import org.jtwig.functions.FunctionRequest;
import org.jtwig.functions.SimpleJtwigFunction;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AbstractUnitUnitPairsFunction extends SimpleJtwigFunction {
  @Override
  public String name() {
    return "abstract_unit_unit_pairs";
  }

  @Override
  public Object execute(final FunctionRequest request) {
    request.minimumNumberOfArguments(1).maximumNumberOfArguments(1);

    @SuppressWarnings("unchecked")
    final List<AbstractUnit> abstractUnits = (List<AbstractUnit>) request.get(0);
    final Set<Pair> pairs = new HashSet<>();

    abstractUnits.forEach(abstractUnit -> abstractUnit
        .getUnits().stream()
        .map(unit -> new Pair(abstractUnit, unit))
        .forEachOrdered(pairs::add));
    return pairs;
  }

  private static class Pair {
    private final Unit unit;
    private final AbstractUnit abstractUnit;

    private Pair(final AbstractUnit abstractUnit, final Unit unit) {
      this.abstractUnit = abstractUnit;
      this.unit = unit;
    }

    public Unit getUnit() {
      return unit;
    }

    public AbstractUnit getAbstractUnit() {
      return abstractUnit;
    }
  }
}
