import type { Metadata } from 'next'
import Sidebar from '@/components/Sidebar'
import CursorGlow from '@/components/CursorGlow'
import { ThemeProvider } from '@/contexts/ThemeContext'
import '@/styles/globals.css'

export const metadata: Metadata = {
  title: 'SAST Analysis Platform',
  description: 'A modern platform for SAST analysis',
}

export default function RootLayout({
  children,
}: {
  children: React.ReactNode
}) {
  return (
    <html lang="en">
      <body>
        <ThemeProvider>
          <div className="flex min-h-screen">
            <Sidebar />
            <main className="flex-1 ml-20 p-8">
              {children}
            </main>
          </div>
          <CursorGlow />
        </ThemeProvider>
      </body>
    </html>
  )
}



import './globals.css'