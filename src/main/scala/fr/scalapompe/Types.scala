package fr.scalapompe
import zio.Scope
import zio.http.Client

object Types {
  type RoutesEnvironment = Client & Scope;
}
