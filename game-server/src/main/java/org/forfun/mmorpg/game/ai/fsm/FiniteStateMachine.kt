package org.forfun.mmorpg.game.ai.fsm

import org.forfun.mmmorpg.game.scene.actor.Creature


open class FiniteStateMachine {

      var initState: State? = null

      var currState: State? = null
    /** 各种状态以及对应的转换规则 */
      val state2Transtions = HashMap<State, MutableList<Transition>>()

    @Volatile
      var running = true

      var freezeTimeOut: Long = 0

    fun addTransition(transition: Transition) {
        var transitions = state2Transtions[transition.fromState()]
        if (transitions == null) {
            transitions = ArrayList()
            state2Transtions[transition.fromState()] = transitions
        }
        transitions.add(transition)
    }

    fun enterFrame(creature: Creature) {
        if (this.currState == null) {
            this.currState = this.initState
            this.currState!!.onEnter(creature)
        }

        val passed = HashSet<String>()
        val clazzName = this.currState!!.javaClass.name

        while (true) {
            if (!running) {
                if (freezeTimeOut > 0 && System.currentTimeMillis() > freezeTimeOut) {
                    running = true
                } else {
                    break
                }
            }

            this.currState!!.execute(creature)
            if (passed.contains(clazzName)) {
                break
            }
            passed.add(clazzName)

            val transitions = state2Transtions[this.currState]
            if (transitions != null) {
                for (transition in transitions) {
                    if (transition.meetCondition(creature)) {
                        this.currState!!.onExit(creature)
                        this.currState = transition.toState()
                        this.currState!!.onEnter(creature)
                    }
                }
            }
        }
    }

    /**
     * 暂停ai
     * @param timeout
     */
    fun freeze(timeout: Long) {
        this.freezeTimeOut = System.currentTimeMillis() + timeout
    }
}
