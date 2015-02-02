package de.stups.slottool

/**
 * Created by David Schneider on 01.02.15.
 */
enum Faculty {
   WiWi("wiwi", [(FileType.BMachine.name): "wiwi_data.mch.template", (FileType.Prolog.name): "data.pl.template"]),
   PhilFak("philfak", [(FileType.BMachine.name): "data.mch.template", (FileType.Prolog.name): "data.pl.template"]);

   private final name
   private final templates

   Faculty(String name, templates) {
      this.name = name
      this.templates = templates
   }

   def getName() {
      return name
   }

   def getTemplates() {
      return templates
   }

   @Override
   def String toString() {
      this.name
   }
}
