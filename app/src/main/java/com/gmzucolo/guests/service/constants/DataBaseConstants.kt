package com.gmzucolo.guests.service.constants

class DataBaseConstants private constructor() {

    /**
     * Tables available at database with yours columns
     */

    object GUEST {
        const val TABLE_NAME = "Guest"

        object COLUMNS {
            const val ID = "id"
            const val NAME = "name"
            const val PRESENCE = "presence"

        }
    }
}