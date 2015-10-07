package de.stups.slottool.data.dao

class ModuleFocusAreaDAO extends AbstractDAO{
    ModuleDAO moduleDAO
    FocusAreaDAO focusAreaDAO

    ModuleFocusAreaDAO(def sql, ModuleDAO moduleDAO, FocusAreaDAO focusAreaDAO) {
        super(sql)
        this.moduleDAO = moduleDAO
        this.focusAreaDAO = focusAreaDAO
    }

    @Override
    protected loadRow(def Object row) {
        def module = moduleDAO.getById(row['module_id'])
        def focus_area = focusAreaDAO.getById(row['focus_area_id'])
        module.focus_areas.add(focus_area)
        focus_area.modules.add(module)
    }

    @Override
    def getById(Object id) {
        throw new UnsupportedOperationException()
    }

    @Override
    String getTableName() {
        return "modules_focus_areas"
    }

    @Override
    Iterator iterator() {
        raise UnsupportedOperationException()
    }
}
