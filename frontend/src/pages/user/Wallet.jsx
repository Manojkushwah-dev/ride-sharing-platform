import { useState, useEffect, useContext } from "react";
import { AuthContext } from "../../auth/AuthContext";
import { getWallet } from "../../api/user.api";
import { addMoneyToWallet } from "../../api/payment.api";
import { getPaymentHistory } from "../../api/payment.api";
import Button from "../../components/common/Button";
import Loader from "../../components/common/Loader";
import Notification from "../../components/common/Notification";

const Wallet = () => {
  const { user } = useContext(AuthContext);
  const [wallet, setWallet] = useState(null);
  const [transactions, setTransactions] = useState([]);
  const [loading, setLoading] = useState(true);
  const [addingMoney, setAddingMoney] = useState(false);
  const [amount, setAmount] = useState("");
  const [notifications, setNotifications] = useState([]);

  useEffect(() => {
    fetchWalletData();
  }, []);

  const fetchWalletData = async () => {
    try {
      setLoading(true);
      const [walletData, paymentHistory] = await Promise.all([
        getWallet(),
        getPaymentHistory().catch(() => []) // Gracefully handle if endpoint doesn't exist
      ]);
      setWallet(walletData);
      setTransactions(paymentHistory || []);
    } catch (err) {
      console.error("Failed to fetch wallet data:", err);
      setNotifications((prev) => [
        ...prev,
        {
          id: Date.now(),
          message: err.response?.data?.message || "Failed to load wallet",
          type: "error",
        },
      ]);
    } finally {
      setLoading(false);
    }
  };

  const handleAddMoney = async (e) => {
    e.preventDefault();
    const amountValue = parseFloat(amount);
    
    if (!amount || isNaN(amountValue) || amountValue <= 0) {
      setNotifications((prev) => [
        ...prev,
        {
          id: Date.now(),
          message: "Please enter a valid amount",
          type: "error",
        },
      ]);
      return;
    }

    setAddingMoney(true);
    try {
      await addMoneyToWallet({ amount: amountValue });
      // Refresh wallet data after adding money
      const updatedWallet = await getWallet();
      setWallet(updatedWallet);
      setAmount("");
      setNotifications((prev) => [
        ...prev,
        {
          id: Date.now(),
          message: `Successfully added $${amountValue.toFixed(2)} to wallet`,
          type: "success",
        },
      ]);
    } catch (err) {
      setNotifications((prev) => [
        ...prev,
        {
          id: Date.now(),
          message: err.response?.data?.message || "Failed to add money",
          type: "error",
        },
      ]);
    } finally {
      setAddingMoney(false);
    }
  };

  const removeNotification = (id) => {
    setNotifications((prev) => prev.filter((n) => n.id !== id));
  };

  if (loading) return <Loader className="min-h-screen" />;

  return (
    <div className="container mx-auto px-4 py-8 max-w-4xl">
      {notifications.map((notif) => (
        <Notification
          key={notif.id}
          message={notif.message}
          type={notif.type}
          onClose={() => removeNotification(notif.id)}
        />
      ))}

      <h1 className="text-3xl font-bold mb-6">My Wallet</h1>

      {/* Wallet Balance Card */}
      <div className="bg-gradient-to-r from-blue-500 to-blue-600 rounded-lg shadow-lg p-6 mb-6 text-white">
        <div className="flex justify-between items-center">
          <div>
            <p className="text-blue-100 text-sm mb-1">Current Balance</p>
            <p className="text-4xl font-bold">
              ${wallet?.balance?.toFixed(2) || "0.00"}
            </p>
          </div>
          <div className="text-5xl">💳</div>
        </div>
      </div>

      {/* Add Money Section */}
      <div className="bg-white rounded-lg shadow-md p-6 mb-6">
        <h2 className="text-xl font-semibold mb-4">Add Money to Wallet</h2>
        <form onSubmit={handleAddMoney} className="space-y-4">
          <div>
            <label className="block text-sm font-medium text-gray-700 mb-1">
              Amount ($)
            </label>
            <input
              type="number"
              step="0.01"
              min="0.01"
              value={amount}
              onChange={(e) => setAmount(e.target.value)}
              required
              className="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
              placeholder="Enter amount"
            />
          </div>
          <Button type="submit" disabled={addingMoney} variant="primary">
            {addingMoney ? <Loader size="sm" /> : "Add Money"}
          </Button>
          <p className="text-xs text-gray-500 mt-2">
            💡 This is a mock transaction. In production, this would integrate with a payment gateway.
          </p>
        </form>
      </div>

      {/* Transaction History */}
      <div className="bg-white rounded-lg shadow-md p-6">
        <h2 className="text-xl font-semibold mb-4">Transaction History</h2>
        {transactions.length === 0 ? (
          <div className="text-center py-8 text-gray-500">
            <p>No transactions yet</p>
            <p className="text-sm mt-2">Your payment history will appear here</p>
          </div>
        ) : (
          <div className="overflow-x-auto">
            <table className="min-w-full divide-y divide-gray-200">
              <thead className="bg-gray-50">
                <tr>
                  <th className="px-4 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                    Date
                  </th>
                  <th className="px-4 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                    Ride ID
                  </th>
                  <th className="px-4 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                    Amount
                  </th>
                  <th className="px-4 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                    Status
                  </th>
                  <th className="px-4 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                    Payment Mode
                  </th>
                </tr>
              </thead>
              <tbody className="bg-white divide-y divide-gray-200">
                {transactions.map((transaction) => (
                  <tr key={transaction.id}>
                    <td className="px-4 py-3 whitespace-nowrap text-sm text-gray-900">
                      {new Date(transaction.createdAt).toLocaleString()}
                    </td>
                    <td className="px-4 py-3 whitespace-nowrap text-sm text-gray-900">
                      {transaction.rideId || "N/A"}
                    </td>
                    <td className="px-4 py-3 whitespace-nowrap text-sm font-medium text-gray-900">
                      ${transaction.amount?.toFixed(2) || "0.00"}
                    </td>
                    <td className="px-4 py-3 whitespace-nowrap">
                      <span
                        className={`px-2 py-1 inline-flex text-xs leading-5 font-semibold rounded-full ${
                          transaction.status === "SUCCESS"
                            ? "bg-green-100 text-green-800"
                            : transaction.status === "FAILED"
                            ? "bg-red-100 text-red-800"
                            : "bg-yellow-100 text-yellow-800"
                        }`}
                      >
                        {transaction.status}
                      </span>
                    </td>
                    <td className="px-4 py-3 whitespace-nowrap text-sm text-gray-500">
                      {transaction.paymentMode || "WALLET"}
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        )}
      </div>
    </div>
  );
};

export default Wallet;

