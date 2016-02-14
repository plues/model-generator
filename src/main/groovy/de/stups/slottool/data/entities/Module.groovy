package de.stups.slottool.data.entities

class Module extends Entity {
    Date updated_at
    int id
    String key
    String name
    Date created_at
    String title
    Integer pordnr
    Boolean mandatory
    Course course
    Integer elective_units
    Set<AbstractUnit> abstract_units
    private Integer credit_points


    Module(Integer id, Level level, String key, String name, String title, Integer pordnr, Integer credit_points, Integer elective_units, Boolean mandatory, Date created_at, Date updated_at) {
        this.id = id
        this.key = key
        this.name = name
        this.title = title
        this.pordnr = pordnr
        this.credit_points = credit_points
        this.elective_units = elective_units
        this.mandatory = mandatory
        this.created_at = created_at
        this.updated_at = updated_at
        this.abstract_units = new HashSet<AbstractUnit>()
    }

    def getCredit_points() {
        if(credit_points == null ) {
            return -1
        }
        return credit_points
    }
}
