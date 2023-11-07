package ru.quipy.projections.exmp

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import ru.quipy.api.ProjectAggregate
import ru.quipy.streams.annotation.AggregateSubscriber

@Service
@AggregateSubscriber(
    aggregateClass = ProjectAggregate::class, subscriberName = "demo-subs-stream"
)
class AnnotationBasedProjectEventsSubscriberExmp {

    val logger: Logger = LoggerFactory.getLogger(AnnotationBasedProjectEventsSubscriberExmp::class.java)

}