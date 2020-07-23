package arcana.resource

import arcana.api.Resource

abstract class ResourceWrapper(private val resource: Resource) : Resource by resource
