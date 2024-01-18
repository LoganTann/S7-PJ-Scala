package fr.scalapompe.controllers

import zio.http.Routes

/** Defines a controller. A controller contains a set of routes.
  */
trait ControllerTrait {
  val routes: Routes[Any, Throwable];
}
