package de.stups.slottool.data.entities

class Level extends Entity {
    Date updated_at
    Date created_at
    String art
    String name
    String tm
    Integer id
    private Integer max
    private Integer min
    private Integer min_credit_points
    private Integer max_credit_points
    Level parent
    Integer parent_id

    Set<Module> modules // has many
    Set<Level> children // has many both are exclusive

    Course course // belongs to one course

    def Level(Integer id, String name, String tm, String art, Integer min, Integer max,
              Integer min_credit_points, Integer max_credit_points, Integer parent_id,
              Date created_at, Date updated_at) {
        this.id = id
        this.name = name
        this.tm = tm
        this.art = art
        this.min = min
        this.max = max
        this.min_credit_points = min_credit_points
        this.max_credit_points = max_credit_points
        this.parent_id = parent_id
        this.created_at = created_at
        this.updated_at = updated_at

        this.modules = new HashSet<Module>()
        this.children = new HashSet<Level>()
    }

    @SuppressWarnings("GroovyUnusedDeclaration")
    def getMin() {
        if(this.min == null) {
            return -1
        }
        return this.min
    }

    @SuppressWarnings("GroovyUnusedDeclaration")
    def getMax() {
        if(this.max == null) {
            return -1
        }
        return this.max

    }

    @SuppressWarnings("GroovyUnusedDeclaration")
    def getMax_credit_points() {
        if(this.max_credit_points == null) {
            return -1
        }
        return this.max_credit_points
    }

    @SuppressWarnings("GroovyUnusedDeclaration")
    def getMin_credit_points() {
        if(this.min_credit_points == null) {
            return -1
        }
        return this.min_credit_points
    }
}
