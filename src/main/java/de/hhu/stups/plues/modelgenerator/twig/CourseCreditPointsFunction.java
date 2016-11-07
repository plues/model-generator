package de.hhu.stups.plues.modelgenerator.twig;

import de.hhu.stups.plues.data.entities.Course;

import org.jtwig.functions.FunctionRequest;
import org.jtwig.functions.SimpleJtwigFunction;

class CourseCreditPointsFunction extends SimpleJtwigFunction {
  @Override
  public String name() {
    return "formatCreditPoints";
  }

  @Override
  public Object execute(final FunctionRequest request) {
    request.minimumNumberOfArguments(1).maximumNumberOfArguments(1);
    final Course course = (Course) request.get(0);
    if (course.getCreditPoints() < 0) {
      return "";
    }
    return "cp=\"" + course.getCreditPoints() + "\"";
  }
}
