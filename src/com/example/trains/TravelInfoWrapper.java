package com.example.trains;

public class TravelInfoWrapper {

	private String transportType;
	private String departureTime;
	private String arrivalTime;
	
	public TravelInfoWrapper(String transportType, String departureTime,
			String arrivalTime) {
		this.setTransportType(transportType);
		this.setDepartureTime(departureTime);
		this.setArrivalTime(arrivalTime);
	}

	public String getTransportType() {
		return transportType;
	}

	public void setTransportType(String transportType) {
		this.transportType = transportType;
	}

	public String getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(String departureTime) {
		this.departureTime = departureTime;
	}

	public String getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(String arrivalTime) {
		this.arrivalTime = arrivalTime;
	}
	
	
}
