# Katana AntiCheat

[![License: AGPL-3.0](https://img.shields.io/badge/License-AGPL--3.0-blue.svg)](https://opensource.org/licenses/AGPL-3.0)
[![Minecraft](https://img.shields.io/badge/Minecraft-1.13+-green.svg)](https://www.minecraft.net/)
[![Java](https://img.shields.io/badge/Java-8+-orange.svg)](https://www.oracle.com/java/)

A powerful and advanced anti-cheat plugin for Minecraft servers, designed to detect and prevent various types of cheating including combat hacks, movement exploits, and packet manipulation.

## 🌟 Features

### Combat Protection
- **AutoClicker Detection** - Multiple algorithms (A-N, P, U, W) to detect various auto-clicker patterns
- **AimAssist Detection** - Advanced aim assistance detection with multiple check types
- **Reach Detection** - Configurable reach distance monitoring with buffer system
- **Velocity Detection** - Anti-velocity hack detection
- **KillAura Detection** - Comprehensive kill aura detection
- **Hitbox Detection** - Invalid hitbox detection

### Movement Protection
- **Fly Detection** - Multiple fly detection algorithms
- **Speed Detection** - Movement speed monitoring
- **Step Detection** - Invalid step detection
- **Motion Detection** - Advanced motion analysis
- **OmniSprint Detection** - Sprint hack detection
- **Water Movement** - Water-based movement exploits
- **Vehicle Movement** - Vehicle-related exploits

### Packet Protection
- **Bad Packets** - Malicious packet detection
- **Network Handler** - Advanced network traffic analysis
- **Transaction Monitoring** - Packet transaction validation
- **Anti-Crash** - Server crash prevention

### Additional Features
- **Anti-VPN** - VPN and proxy detection
- **Client Brand Detection** - Unauthorized client detection
- **Discord Integration** - Webhook notifications
- **Database Support** - MySQL, MongoDB, and SQLite support
- **Ban Wave System** - Mass ban management
- **GUI Management** - User-friendly interface
- **Performance Monitoring** - TPS and server performance tracking

## 📋 Requirements

### Server Requirements
- **Minecraft Version**: 1.13+
- **Java Version**: 8 or higher
- **Server Software**: Spigot/Paper (recommended)

### Dependencies
- **ProtocolLib** (soft dependency)
- **ViaVersion** (soft dependency)
- **ProtocolSupport** (soft dependency)

## 🚀 Installation

1. **Download** the latest release from the releases page
2. **Place** the `Katana.jar` file in your server's `plugins` folder
3. **Start** your server to generate configuration files
4. **Stop** the server and configure the plugin
5. **Restart** your server

## ⚙️ Configuration

### Main Configuration (`config.yml`)

The main configuration file contains settings for:
- **Alerts and Messages** - Customize alert formats and messages
- **Punishments** - Configure ban/kick commands and messages
- **Anti-Crash** - Server crash prevention settings
- **Network Handler** - Advanced packet monitoring
- **Anti-VPN** - VPN detection settings
- **Discord Integration** - Webhook configuration

### Checks Configuration (`checks.yml`)

Configure individual anti-cheat checks:
- **Combat Checks** - AutoClicker, AimAssist, Reach, Velocity, KillAura
- **Movement Checks** - Fly, Speed, Step, Motion, OmniSprint
- **Packet Checks** - Bad packets, network monitoring
- **World Checks** - Block-related exploits

Each check can be configured with:
- `enabled`: Enable/disable the check
- `autoban`: Automatic banning
- `punish-vl`: Violation level for punishment
- `mode`: Punishment mode (BAN/KICK)
- `banwave`: Include in ban waves

## 🎮 Commands

| Command | Permission | Description |
|---------|------------|-------------|
| `/katana alerts` | `katana.staff` | Toggle alerts on/off |
| `/katana gui` | `katana.staff` | Open management GUI |
| `/katana logs` | `katana.staff` | View violation logs |
| `/katana info <player>` | `katana.staff` | View player information |
| `/katana reload` | `katana.staff` | Reload configuration |
| `/katana banwave` | `katana.staff` | Manage ban waves |

## 🔧 Permissions

| Permission | Default | Description |
|------------|---------|-------------|
| `katana.staff` | `op` | Access to all Katana commands |
| `katana.bypass` | `op` | Bypass all anti-cheat checks |

## 🗄️ Database Support

Katana supports multiple database types:

### SQLite (Default)
- No additional setup required
- Data stored locally in `plugins/Katana/data.db`

### MySQL
```yaml
database: mysql
```

### MongoDB
```yaml
database: mongodb
```

## 🔗 Discord Integration

Configure Discord webhooks for real-time notifications:

```yaml
discord:
  enabled: true
  alert-webhook-url: "YOUR_WEBHOOK_URL"
  crash-webhook-url: "YOUR_CRASH_WEBHOOK_URL"
  send-alerts: true
  send-bans: true
```

## 🛠️ Development

### Building from Source

1. **Clone** the repository
2. **Install** Maven dependencies: `mvn clean install`
3. **Build** the project: `mvn clean package`
4. **Find** the compiled JAR in the `target` folder

### Dependencies

- **PacketEvents** - Advanced packet handling
- **Lombok** - Code generation
- **FastUtil** - High-performance collections
- **Apache Commons Math** - Mathematical utilities
- **MongoDB Driver** - MongoDB support

## 📄 License

This project is licensed under the **GNU Affero General Public License v3.0** - see the [LICENSE](LICENSE) file for details.

## 🤝 Credits

- **Author**: maaattn & ricodevvv
- **Made for**: Imanity Network
- **Special Thanks**:
  - Karhu Anticheat - Messages
  - Intave Anticheat - AntiCrash
  - ImanityVPN - AntiVPN
  - PacketEvents - AntiCrash and check improvements

## ⚠️ Disclaimer

This anti-cheat is designed to detect and prevent cheating on Minecraft servers. However, no anti-cheat is perfect, and false positives may occur. Always review violations before taking action against players.

## 📞 Support

For support, issues, or questions:
- Create an issue on GitHub
- Join our Discord server (if available)
- Check the configuration files for detailed settings

---

**Note**: This anti-cheat is designed to work with gaming chairs and long arms not being allowed boii

- matthew.tf
