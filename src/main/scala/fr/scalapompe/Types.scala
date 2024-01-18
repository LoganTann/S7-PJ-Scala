package fr.scalapompe
import zio.Scope
import zio.http.Client

object Types {

  /** Injected (required) environments :
    *   - Client : ZIO HTTP client
    *   - Scope : ZIO scope
    */
  type RoutesEnvironment = Client & Scope;
}
