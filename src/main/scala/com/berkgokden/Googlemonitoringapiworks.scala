package com.berkgokden

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.monitoring.v3.{Monitoring, MonitoringScopes}

object Googlemonitoringapiworks extends App {
  println("Hello, google-monitoring-api-works")

  val project = "your-project-name" // you should have one on google.
  val projectResource = "projects/" + project

  // Grab the Application Default Credentials from the environment.
  // Download authentication json form Google Cloud and Put conf.json into resources folder!
  val resourceAsStream = getClass.getResourceAsStream("/conf.json")
  println("ok")
  val credential = GoogleCredential.fromStream(resourceAsStream)
    .createScoped(MonitoringScopes.all())

  println(credential.getServiceAccountId) // this only works if you have a service account

  // Create and return the CloudMonitoring service object
  val httpTransport = new NetHttpTransport()
  val jsonFactory = new JacksonFactory()
  val service = new Monitoring.Builder(httpTransport,
    jsonFactory, credential)
    .setApplicationName("Monitoring Sample")
    .build()

  val res = service.projects().metricDescriptors().list(projectResource).execute()
  println(res.toPrettyString)
}
