import api from "./axios";

/**
 * Get user wallet
 * @returns {Promise} Wallet object with balance
 */
export const getWallet = async () => {
  const response = await api.get("/api/users/me/wallet");
  return response.data;
};

/**
 * Add money to wallet
 * @param {Object} data - { amount: number }
 * @returns {Promise} Updated wallet object
 */
export const addMoney = async (data) => {
  const response = await api.post("/api/users/me/wallet/add", data);
  return response.data;
};

/**
 * Get user profile
 * @returns {Promise} User profile object
 */
export const getProfile = async () => {
  const response = await api.get("/api/users/me");
  return response.data;
};

/**
 * Update user profile
 * @param {Object} profileData - Profile data to update
 * @returns {Promise} Updated profile object
 */
export const updateProfile = async (profileData) => {
  const response = await api.put("/api/users/me", profileData);
  return response.data;
};

