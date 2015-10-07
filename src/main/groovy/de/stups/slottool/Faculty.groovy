package de.stups.slottool

/**
 * Created by David Schneider on 01.02.15.
 */
enum Faculty {
   WiWi("wiwi"),
   PhilFak("philfak")

   private final name

   Faculty(String name) {
      this.name = name
   }

   def getName() {
      return name
   }

   @Override
   def String toString() {
      this.name
   }
}
