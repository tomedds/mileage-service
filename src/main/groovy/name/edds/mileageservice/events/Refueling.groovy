package name.edds.mileageservice.events

import groovy.transform.TypeChecked

/**
 * Data for a fueling stop
 */
@TypeChecked
class Refueling extends Event {

    float pricePerGallon
    float numberOfGallons
    boolean partialFill

}

