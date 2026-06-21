package org.forfun.mmorpg.game

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.DependsOn
import org.springframework.stereotype.Component

@Component
@DependsOn("serverConfigFactory")
class CrossConfig {

    @Value("\${rpc.center.id}")
    var centerId: Int = 0

    @Value("\${rpc.center.ip}")
    var centerIp: String? = null

    @Value("\${rpc.center.port}")
    var centerPort: Int = 0

    @Value("\${rpc.signKey}")
    var sign: String? = null
}
