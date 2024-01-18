package fr.scalapompe.controllers

import zio.Scope
import zio.http._
import fr.scalapompe.Types.RoutesEnvironment

/** Defines a controller. A controller contains a set of routes.
  */
trait ControllerTrait {
  val routes: Routes[RoutesEnvironment, Throwable];
}
