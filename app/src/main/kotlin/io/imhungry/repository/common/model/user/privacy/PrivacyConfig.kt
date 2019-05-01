package io.imhungry.repository.common.model.user.privacy

data class PrivacyConfig(
    val favorites: PrivacyLevel = PrivacyLevel.PUBLIC,
    val friendsList: PrivacyLevel = PrivacyLevel.PUBLIC,
    val history: PrivacyLevel = PrivacyLevel.PUBLIC
)