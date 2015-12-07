package de.stups.slottool.data.entities

class Entity {
    boolean dirty = false
    Map original_data = [:]

    @Override
    public void setProperty(String propertyName, Object newValue) {
        // on first change to field save original value
        if(!this.dirty) {
            this.original_data[propertyName] = this.getProperty(propertyName)
        }
        this.@dirty = true
        def metaProperty = this.metaClass.getMetaProperty(propertyName)
        if(metaProperty) {
            metaProperty.setProperty(this, newValue)
        }
    }
}
