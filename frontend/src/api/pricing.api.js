import api from "./axios";

/**
 * Calculate fare for a ride from locations
 * @param {Object} data - { pickupLocation: string, dropoffLocation: string, rideId?: number }
 * @returns {Promise} Fare response with totalFare and surgeMultiplier
 */
export const calculateFare = async (data) => {
  const response = await api.post("/api/pricing/calculate", data);
  return response.data;
};

/**
 * Estimate fare for a ride (without creating a ride)
 * @param {Object} data - { distanceKm: number, timeMinutes: number }
 * @returns {Promise} Fare response with total and surge
 */
export const estimateFare = async (data) => {
  const response = await api.post("/api/pricing/estimate", data);
  return response.data;
};

